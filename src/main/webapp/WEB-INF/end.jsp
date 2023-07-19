<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Game over</title>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<div class="position-absolute top-50 start-50 translate-middle" style="width: 300px; text-align: center">
    <div class="card" style="background-color: rgba(47, 79, 79, .2);">
        <div class="card-header" style="color: deepskyblue;">
            The game is over
        </div>
        <div class="card-body">
            <h5 class="card-title">
                <div class="end-message">
                    ${requestScope.get("theEnd")}
                </div>
            </h5>
            <br>
            <div style="color: orange">
                <c:forEach items="${sessionScope.player.getPlayerStatistic()}" var="stat">
                    <li>
                            ${stat.getKey()} : ${stat.getValue()}
                    </li>
                </c:forEach>
            </div>
            <br>
            <form action="${pageContext.request.contextPath}/quest">
                <button class="btn btn-outline-primary" type="submit">Start again</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
