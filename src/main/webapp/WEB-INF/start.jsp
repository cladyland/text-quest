<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Start</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="start-game">
    <div class="position-absolute top-50 start-50 translate-middle">
        <h3>Hello, ${sessionScope.player.getNickName()}</h3>
        <br>
        Are you ready to start your adventure?
        <br><br>
        <form action="${pageContext.request.contextPath}/quest" method="post">
            <button class="btn btn-outline-light" type="submit">Lets go!</button>
        </form>
    </div>
</div>
</body>
</html>
