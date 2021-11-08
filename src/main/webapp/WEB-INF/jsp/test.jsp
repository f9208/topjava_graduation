<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>

<c:url var="reducedImagePath" value="${reducedImagePath}"/>
<body>

<form action="test" method="post" enctype="multipart/form-data" name="inputFile">
    <input type="file" name="inputFile"><br/>
    Name: <input type="text" name="name"><br/> <br/>
    <input type="submit" value="Upload"> Press here to upload the file
    <c:if test="${reducedImagePath!='0'}">
        <img src="${reducedImagePath}"/>
    </c:if>
</form>
</body>
</html>
