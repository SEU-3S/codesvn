<%@ WebHandler Language="C#" Class="MyHandler" %>
using System;
using System.Web;

public class MyHandler : IHttpHandler
{
    public bool IsReusable
    {
        get
        {
            return false;
        }
    }

    // Read current progress value from application context using sessionId provided in the request.
    public void ProcessRequest(HttpContext context)
    {
        int percent = -1;

        string sessionId = context.Request["sessionId"];
        if (sessionId != null)
        {
            Object percentObj = context.Application[sessionId];
            if (percentObj != null)
            {
                percent = Convert.ToInt32(percentObj.ToString());
                if (percent == -1)
                {
                    context.Application.Remove(sessionId);
                }
            }
        }
        
        context.Response.ContentType = "text/plain";
        context.Response.Write(percent);
    }
}

