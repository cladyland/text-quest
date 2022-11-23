<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src=https://code.jquery.com/jquery-3.6.0.min.js></script>
    <title>Title</title>
</head>
<body>
<h3>${question}</h3>
<c:forEach items="${answers}" var="answer">
    <form action="${pageContext.request.contextPath}/quest" method="post">
        <input type="hidden" name="answer" value="${answer.getId()}">
        <button type="submit">${answer.getContext()}</button>
    </form>
</c:forEach>
</body>
</html>
