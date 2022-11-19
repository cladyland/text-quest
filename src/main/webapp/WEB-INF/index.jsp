<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <title>Welcome</title>
</head>
<body>
<h1>Prologue</h1>
Stories...<br>
<form action="${pageContext.request.contextPath}/register" method="post">
    <label for="input-nick-name">Name yourself:</label><br>
    <input type="text" id="input-nick-name" name="nickName" minlength="3" maxlength="20" required size="30">
    <button type="submit">Lets go</button>
</form>

${requestScope.get("wrongNickName")}

</body>
</html>
