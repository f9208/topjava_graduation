<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<head>
    <meta charset="UTF-8">
    <title>Не можете выбрать в какой кабак пойти? мы поможем!</title>
    <link rel="stylesheet" type="text/css" href=
    <c:url value="/resources/css/styles.css"/>
</head>

<body>
<header>
    <jsp:include page="fragments/head.jsp"/>
</header>
<main>
    <sec:authorize access="hasRole('ADMIN')" var="isAdmin"/>
    <div class="main_restaurants_list">
        <c:forEach items="${restaurants}" var="restaurant">
            <c:if test="${restaurant.enabled==true || isAdmin==true}">
                <c:url value="${restaurant.label.linkReduced}" var="restaurant_label"/>
                <div class="restaurant_item">
                    <jsp:useBean id="restaurant" class="ru.f9208.choiserestaurant.model.entities.Restaurant"/>
                    <div id="name" style="font-weight: 700; padding-bottom: 5px">${restaurant.name}</div>
                    <div id="description"> ${restaurant.description} </div>
                    <c:if test="${restaurant.enabled==false}">
                        <div id="disabled"> не доступен для пользователей </div>
                    </c:if>
                    <a href="restaurants/${restaurant.id}">
                        <img class="restaurant_label_img" src="${restaurant_label}">
                    </a>
                    <div id="vote"> количество голосов: ${restaurant.vote.size()}</div>
                </div>
            </c:if>
        </c:forEach>
    </div>
    <a href="test">to test</a>
</main>
<footer>
    <jsp:include page="fragments/bottom.jsp"/>
</footer>
</body>
</html>
