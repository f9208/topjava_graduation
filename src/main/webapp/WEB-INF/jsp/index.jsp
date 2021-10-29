<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
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

    <div class="main_restaurants_list">
        <c:forEach items="${restaurants}" var="restaurant">
            <c:url value="${restaurant.label}" var="restaurant_label"/>
            <div class="restaurant_item">
                <jsp:useBean id="restaurant" class="ru.f9208.choiserestaurant.model.entities.Restaurant"/>
                <div id="name" style="font-weight: 700; padding-bottom: 5px">${restaurant.name}</div>
                <div id="description"> ${restaurant.description} </div>
                <a href="restaurants/${restaurant.id}">
                    <img class="restaurant_label_img" src="${restaurant_label}">
                </a>
            </div>
        </c:forEach>
    </div>
</main>
<footer>
    <jsp:include page="fragments/bottom.jsp"/>
</footer>
</body>
</html>
