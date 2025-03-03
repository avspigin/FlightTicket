<%@ page import="ru.anton.je.jdbc.service.TicketService" %>
<%@ page import="ru.anton.je.jdbc.dto.TicketDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>            <%--Когда подгрузили jstl и jstl-api, пишем этот код--%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<%@ include file="header.jsp"%>
<h1>Список билетов</h1>
<a href="${pageContext.request.contextPath}/flights">Возврат к списку полетов</a>
<ul>
    <c:if test="${not empty requestScope.tickets}">     <%--Проверяет если не пустой--%>
        <c:forEach var="ticket" items="${requestScope.tickets}">       <%--Форич, ticket-билет, requestScope.tickets - переданные атрибуты с сервлета--%>
            <li>${fn:toLowerCase(ticket.seatNo())}</li>     <%--fn:toLowerCase() Меняет регистр на строчные--%>
        </c:forEach>
    </c:if>
</ul>
</body>
</html>
