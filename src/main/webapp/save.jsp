<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<img src="${pageContext.request.contextPath}/static/images/image.png" />
<a href="hello-servlet">Hello no</a>
</body>
</html>