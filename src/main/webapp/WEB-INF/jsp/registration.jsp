<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="post">
    <label for="name">Name:
        <input type="text" name="name" id="name" required>
    </label><br/>
    <label for="birthday">Birthday:
        <input type="date" name="birthday" id="birthday">
    </label><br/>
    <label for="email">Email:
        <input type="text" name="email" id="email" required>
    </label><br/>
    <label for="pwd">Password:
        <input type="password" name="pwd" id="pwd" required>
    </label><br/>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option label="${role}">${role}</option>
        </c:forEach>
    </select><br/>
    <c:forEach var="gender" items="${requestScope.genders}">
        <input type="radio" name="gender" value="${gender}">${gender}<br/>
    </c:forEach>
    <input type="submit" value="Send">
</form>
<a href="${pageContext.request.contextPath}/login">
    <button type="button">Login</button>
</a>
<c:if test="${not empty requestScope.errors}">
    <div style="color: red">
        <c:forEach var="error" items="${requestScope.errors}">
            <span>${error.message}</span>
            <br/>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
