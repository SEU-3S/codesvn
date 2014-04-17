<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="com.scand.fileupload.*" %>
<%@ page import="java.io.*" %>
<%@ page import="java.util.*" %>
<%

String uploadFolder = "c:\\upload\\";

// Check that we have a file upload request
boolean isMultipart = FileUpload.isMultipartContent(request);

if (!isMultipart) {

	out.println ("Use multipart form to upload a file!");

} else {

String fileId = request.getParameter("sessionId").toString().trim();
        
// Create a new file upload handler
FileItemFactory factory = new ProgressMonitorFileItemFactory(request, fileId);
ServletFileUpload upload = new ServletFileUpload(factory);

// Parse the request
List /* FileItem */ items = upload.parseRequest(request);

// Process the uploaded items
Iterator iter = items.iterator();
while (iter.hasNext()) {
    FileItem item = (FileItem) iter.next();

    if (item.isFormField()) {
        //processFormField
    } else {
        //processUploadedFile
		String fieldName = item.getFieldName();
		String fileName = item.getName();
		int i2 = fileName.lastIndexOf("\\");
		if(i2>-1) fileName = fileName.substring(i2+1);
		File dirs = new File(uploadFolder);
		//dirs.mkdirs();

		File uploadedFile = new File(dirs,fileName);
		item.write(uploadedFile);
      
		session.setAttribute("FileUpload.Progress."+fileId,"-1");
    }
 }

}  
%>