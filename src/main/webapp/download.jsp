<%-- 
    Document   : download
    Created on : 27-nov-2014, 16.26.10
    Author     : Mauro
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    

  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
        <script src="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
        <link href="//ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/ui-lightness/jquery-ui.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery.fileDownload.js" type="text/javascript"></script>
    <script type="text/javascript">
    $(document).on("click", "a.fileDownloadSimpleRichExperience", function () {
    $.fileDownload($(this).prop('href'), {
        preparingMessageHtml: "We are preparing your report, please wait...",
        failMessageHtml: "There was a problem generating your report, please try again."
    });
    return false; //this is critical to stop the click event which will trigger a normal file download!
});
</script>
</head>
    <body>
       <table>
        <tr>
            <th>File Name</th>
            <th>Description</th>
        </tr>

            <tr>
                <td>
                    <a class="fileDownloadSimpleRichExperience" href="Servlet?operazione=downloadAllegato">Report0.pdf</a>
                </td>
                <td>
                        This file download will succeed
                </td>
            </tr>
            <tr>
                <td>
                    <a class="fileDownloadSimpleRichExperience" href="/FileDownload/DownloadReport/1">Report1.pdf</a>
                </td>
                <td>
                        This file download will fail
                </td>
            </tr>
            <tr>
                <td>
                    <a class="fileDownloadSimpleRichExperience" href="/FileDownload/DownloadReport/2">Report2.pdf</a>
                </td>
                <td>
                        This file download will succeed
                </td>
            </tr>
            <tr>
                <td>
                    <a class="fileDownloadSimpleRichExperience" href="/FileDownload/DownloadReport/3">Report3.pdf</a>
                </td>
                <td>
                        This file download will fail
                </td>
            </tr>
            <tr>
                <td>
                    <a class="fileDownloadSimpleRichExperience" href="/FileDownload/DownloadReport/4">Report4.pdf</a>
                </td>
                <td>
                        This file download will succeed
                </td>
            </tr>
    </table> 
    </body>
</html>
