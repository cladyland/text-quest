<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Welcome</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link href="${pageContext.request.contextPath}/css/style.css" rel="stylesheet">
</head>
<body>
<br>
<div class="shadow p-3 mb-5 bg-body rounded">
    <h1>Prologue</h1>
    You are at the spaceport and ready to board your spaceship. <br>
    Isn't that your dream? Become the captain of an intergalactic ship with a crew that will travel through space and
    seek adventure under your command. <br>
    Let's start!
    <br><br><br>
    <h2>Acquaintance with the crew</h2>
    When you boarded the spaceship, you were greeted by a tall girl holding a black folder in her hands.<br>
    - Hello, commander! I'm Jessica, your assistant. Our navigator Bob is drinking coffee there at the computer.
    The two discussing by the porthole are our flight engineers, Ron and Mary.
    That strange man mixing the liquids is our doctor Tanaka.<br>
    And what is your name?
</div>
<div style="width:400px;">
    <form action="${pageContext.request.contextPath}/register" method="post">
        <input class="border border-success p-1 mb-2 border-opacity-75 border-3"
               type="text" id="input-nick-name" name="nickName" minlength="3" maxlength="20" required size="30">
        <button class="btn btn-outline-success" type="submit">name myself</button>
    </form>
</div>
<div class="wrong-name" style="width: 450px">
    ${requestScope.get("wrongNickName")}
</div>
</body>
</html>
