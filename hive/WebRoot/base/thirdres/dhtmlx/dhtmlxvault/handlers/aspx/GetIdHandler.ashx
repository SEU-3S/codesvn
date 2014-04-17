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

    // Generate unique ID, save it in application with zero progress value and return this ID back to the client.
    // Clinet then will use this ID to read current progress in "GetInfoHandler.ashx".
    public void ProcessRequest(HttpContext context)
    {
        Guid id = Guid.NewGuid();
        context.Application.Add(id.ToString(), 0);

        context.Response.ContentType = "text/plain";
        context.Response.Write(id);
    }
}

