<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: pavel
  Date: 12.09.2019
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create the player</title>
</head>
<body>

<h1>Create player ${createPlayerStep}</h1>

<form:form method="post" modelAttribute="player">
    First name: <form:input path="name"/><br>
    <form:errors path="name" cssClass="error"/><br>
    <input type="submit">
</form:form>

</body>
</html>
