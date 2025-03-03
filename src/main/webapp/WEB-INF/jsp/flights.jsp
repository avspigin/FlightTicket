<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp"%>
<h1>Список перелетов</h1>
<ul>
    <c:if test="${not empty requestScope.flights}">
        <c:forEach var="flight" items="${requestScope.flights}">
            <li><a href="${pageContext.request.contextPath}/tickets?flightId=${flight.id()}">${flight.description()}</a>   <%--{pageContext.request.contextPath} - Когда мы настраиваем Томкат, мы указываем рутовый путь "/", если вдруг нет, то contextPath выручает. В общем всегда вставляем--%>
            </li>
        </c:forEach>
    </c:if>
</ul>
</body>
</html>
