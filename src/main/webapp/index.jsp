<%--
  Created by IntelliJ IDEA.
  User: pc
  Date: 4/4/2024
  Time: 2:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="sendEmail" method="post">
    <input type="email" name="to" placeholder="Recipient's email" required>
    <input type="text" name="subject" placeholder="Subject" required>
    <textarea name="message" placeholder="Your message" required></textarea>
    <button type="submit">Send Email</button>
</form>

</body>
</html>
