<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src=https://code.jquery.com/jquery-3.6.0.min.js></script>
    <title>Start</title>
</head>
<body>
<h1>Hello, ${sessionScope.get("nickName")}</h1>
<br>
Are you ready?
<form action="${pageContext.request.contextPath}/quest" method="post">
    <button type="submit">Start</button>
</form>
</body>
</html>
