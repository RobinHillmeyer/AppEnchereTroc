<%--
  Created by IntelliJ IDEA.
  User: boute
  Date: 02/06/2022
  Time: 00:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<jsp:include page="fragments/head.jsp" />

<body class="mainHome">
<!--////	TEST UPLOAD IMG		////-->
<div class="mainHome">
    <form method="post" action="ServletUpload" enctype='multipart/form-data'>

        <input type="file" name="file" />
        <input type="submit" value="Upload"/>

    </form>

    <h4>This is Ajax</h4>

    <input id="ajaxfile" type="file"/>
    <button onclick="uploadFile()">Upload</button>

    <script>
        async function uploadFile(){
            let formData = new FormData();
            formData.append("file", ajaxfile.files[0]);
            await fetch('ServletUpload',{
                method:"POST",
                body: formData
            });
            alert("L'img à été télécharger avec succes");

        }
    </script>



</div>
</body>
</html>
