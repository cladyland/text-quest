<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<br><br>
<div class="position-absolute top-50 start-50 translate-middle">
    <div class="card text-bg-secondary mb-3" style=" text-align: center">
        <div class="card-body" style="background-color: darkslateblue">
            <h5 class="card-title">${question}</h5>
            <br>
            <c:forEach items="${answers}" var="answer">
                <form action="${pageContext.request.contextPath}/quest" method="post">
                    <input type="hidden" name="playerAnswerId" value="${answer.getId()}">
                    <button class="btn btn-outline-warning" type="submit">${answer.getContext()}</button>
                </form>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>
