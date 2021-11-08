<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <meta charset="UTF-8">
    <title>Не можете выбрать в какой кабак пойти? мы поможем!</title>
    <link rel="stylesheet" type="text/css" href=
    <c:url value="/resources/css/styles.css"/>>
</head>

<body>
<sec:authorize access="isAuthenticated()" var="isAdmin"/>
<c:set value="${restaurant}" var="restaurant"/>
<c:url value="${restaurant.label}" var="label"/>
<c:set value="${errors}" var="errors"/>

<header>
    <jsp:include page="fragments/head.jsp"/>
</header>
<%--<c:if test="${isAdmin}">--%>
<div><h2 style="text-align: center;"> ${restaurant.name}</h2></div>
<div><img class="label_img_fixed" src="${label}"/></div>
<%--@elvariable id="restaurant" type="ru.f9208.choiserestaurant.model.entities.Restaurant"--%>
<form:form modelAttribute="restaurant" method="post"
           cssStyle="margin-left: auto;margin-right: auto;width: 1400px;" enctype="multipart/form-data">

    <table><c:forEach items="${restaurant.menu}" var="dish" varStatus="status">
        <tr>
            <td><input name="menu[${status.index}].name" value="${dish.name}"></td>
            <td><input name="menu[${status.index}].price" value="${dish.price}"></td>
            <td><c:if test="${errors[(dish.id)]==true}">необходимо ввести название и цену продукта!</c:if></td>
            <input type="hidden" name="menu[${status.index}].id" value="${dish.id}"/>
        </tr>
    </c:forEach>

        <tr>
            <td><label for="name">Name</label></td>
            <td><form:input path="name"/> <form:errors path="name"/></td>
        </tr>
        <tr>
            <td><label for="description">description: </label></td>
            <td><form:input path="description"/></td>
            <td><input type="hidden" name="label" value="${restaurant.label}"/></td>
        </tr>
        <tr>
            <td><form:checkbox path="enabled"/></td>
            <td> доступность для голосования</td>
        </tr>
        <tr>
            <td><input type="file" name="file"><br/>
            </td>
        </tr>
        <tr>
            <td>
                <button type="submit"> send</button>
            </td>
        </tr>
    </table>
</form:form>
<%--</c:if>--%>

<br>
<br>
<%--@elvariable id="dish" type="ru.f9208.choiserestaurant.model.entities.Dish"--%>
<c:url var="add" value="/restaurants/${restaurant.id}/add"/>
<form:form modelAttribute="dish" action="${add}" method="post">
    <label>dish name: </label>
    <form:input path="name"/>
    <form:errors path="name"/>
    <form:input path="price"/> <form:errors path="price"/>
    <button type="submit"> добавить блюдо</button>
</form:form>
<footer>
    <jsp:include page="fragments/bottom.jsp"/>
</footer>
</body>
</html>
