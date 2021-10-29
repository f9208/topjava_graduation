<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <meta charset="UTF-8">
    <title>Не можете выбрать в какой кабак пойти? мы поможем!</title>

    <link rel="stylesheet" type="text/css" href=
    <c:url value="/resources/css/styles.css"/>>
</head>

<body>
<sec:authorize access="isAuthenticated()" var="isAdmin"/>

<header>
    <jsp:include page="fragments/head.jsp"/>
</header>

<c:set value="${restaurant}" var="restaurant"/>
<c:if test="${isAdmin}">
    <%--@elvariable id="restaurant" type="ru.f9208.choiserestaurant.model.entities.Restaurant"--%>
    <form:form modelAttribute="restaurant" method="post"
               cssStyle="margin-left: auto;margin-right: auto;width: 400px;">
        <table>
            <tr>
                <td><label for="name">Name</label></td>
                <td><form:input path="name"/> <form:errors path="name"/></td>
            </tr>
            <tr>
                <td><label for="description">description: </label></td>
                <td><form:input path="description"/> <form:errors path="description"/></td>
            </tr>
            <tr>
                <td>
                    <button type="submit" onclick="${edit}"> send</button>
                </td>
            </tr>
        </table>
    </form:form>
</c:if>
<footer>
    <jsp:include page="fragments/bottom.jsp"/>
</footer>
</body>
</html>
