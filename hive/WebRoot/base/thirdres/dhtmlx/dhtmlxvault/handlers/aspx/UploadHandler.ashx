<%@ WebHandler Language="C#" Class="MyHandler" %>
using System;
using System.Web;
using System.Reflection;
using System.IO;
using ScandVault;

public class MyHandler : IHttpHandler
{
    public bool IsReusable
    {
        get
        {
            return false;
        }
    }

    // in-application property indicating current upload progress. It is then read by "GetInfoHandler.ashx"
    private string CurrentSize
    {
        set
        {
            HttpContext.Current.Application[HttpContext.Current.Request.QueryString["sessionId"].ToString()] = value;
        }
    }

    private const string UploadFolder = @"c:\upload\";

    // For debugging purposes
    private StreamWriter _debug = null;//new StreamWriter(UploadFolder + "upload.log", true);
    private void debug(string msg)
    {
        if (_debug != null)
        {
            _debug.WriteLine(DateTime.Now.ToString() + " " + msg);
            _debug.Flush();
        }
    }

    public void ProcessRequest(HttpContext context)
    {
        if (context.Request.QueryString["sessionId"].ToString() != "")
        {
            FileProcessor fp = new FileProcessor(UploadFolder);
            fp.CurrentFileName = context.Request.QueryString["fileName"];

            HttpWorkerRequest workerRequest = (HttpWorkerRequest)context.GetType().GetProperty("WorkerRequest", BindingFlags.Instance | BindingFlags.NonPublic).GetValue(context, null);
            if (workerRequest.HasEntityBody())
            {
                try
                {
                    long contentLength = long.Parse((workerRequest.GetKnownRequestHeader(HttpWorkerRequest.HeaderContentLength)));
                    long CurrentBytesTransfered = 0;

                    // Read boundary marker from Content-Type header
                    bool BoundaryFound = false;
                    string ct = workerRequest.GetKnownRequestHeader(HttpWorkerRequest.HeaderContentType);
                    if (ct != null)
                    {
                        const string pattern = "boundary=";
                        int i = ct.IndexOf(pattern);
                        if (i > -1)
                        {
                            fp.Boundary = ct.Substring(i + pattern.Length);
                            BoundaryFound = true;
                            debug("boundary:" + fp.Boundary);
                        }
                    }
                    if (!BoundaryFound) throw new Exception("Multipart form boundary not found.");

                    // Try to read preloaded data. It is usually empty for Win2008 server
                    byte[] preloadedBufferData = workerRequest.GetPreloadedEntityBody();
                    if (preloadedBufferData != null)
                    {
                        CurrentBytesTransfered += preloadedBufferData.Length;
                        fp.ProcessBuffer(ref preloadedBufferData, true);
                        debug("preloaded:" + Convert.ToString(preloadedBufferData.Length) + "bytes");
                    }

                    // Read the rest of uploaded file
                    if (!workerRequest.IsEntireEntityBodyIsPreloaded())
                    {
                        byte[] bufferData = new byte[48 * 1024];
                        do
                        {
                            // Ask the worker request for the buffer chunk.
                            int rest = (int)Math.Min((long)bufferData.Length, contentLength - CurrentBytesTransfered);
                            debug("read:");
                            long receivedCount = workerRequest.ReadEntityBody(bufferData, rest);
                            debug(Convert.ToString(receivedCount) + "bytes");

                            if (receivedCount > 0)
                            {
                                // Update the status object.
                                CurrentBytesTransfered += receivedCount;

                                // Save read data to the disk file
                                fp.ProcessBuffer(ref bufferData, true);

                                // Add the upload status to the application object. 
                                CurrentSize = Convert.ToString((CurrentBytesTransfered * 100 / contentLength));
                            }
                            // Clear array for new usage
                            Array.Clear(bufferData, 0, bufferData.Length);

                        } while (CurrentBytesTransfered < contentLength);
                    }
                }
                catch (Exception ex)
                {
                    CurrentSize = "-1";
                }
                finally
                {
                    fp.Dispose();
                    fp.CloseStreams();
                    CurrentSize = "-1";
                }
            }
        }
        if (_debug != null) _debug.Close();
    }

    private string getFileName(string path)
    {
        string[] arr = path.Split(new string[] { @"\" }, StringSplitOptions.None);
        return arr[arr.Length - 1].ToString();
    }
}

