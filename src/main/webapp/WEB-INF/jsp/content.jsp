<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 27.02.2025
  Time: 9:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <span>CONTENT РУССКИЙ</span>
    <p>Size: ${requestScope.flights.size()}</p>
    <p>Id: ${requestScope.flights[1].id()}</p>  <%--requestScope получает переданные параметры. Флайтс это массив, и тут к нему возможно обращатся по индексу, т.е. не нужно get()--%>
    <p>Description: ${requestScope.flights.get(0).description()}</p>  <%--Типа так не стоит, при нуле словим исключение--%>
    <p>PARAM id: ${param.id}</p>
    <p>HEADER id: ${header["cookie"]}</p>
    <p>NOT EMPTY: ${not empty flights}</p>

    <%-- Вывод
    CONTENT РУССКИЙ
    Size: 9
    Id: 2
    Description: LDN - MNK - ARRIVED
    PARAM id: 5
    HEADER id: JSESSIONID=64240DD1F6491162FDD4C95B262925F2
    NOT EMPTY: true--%>


</div>
</body>
</html>
