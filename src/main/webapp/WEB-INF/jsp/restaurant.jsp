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
    <c:url value="/resources/css/styles.css"/>
            </head>

<body>
<header>
    <jsp:include page="fragments/head.jsp"/>
</header>

<sec:authorize access="isAuthenticated()" var="isAdmin"/>
<sec:authorize access="isAnonymous()" var="isAnon"/>
<c:set value="${restaurant}" var="restaurant"/>
<c:url value="${restaurant.label}" var="restaurant_label"/>

<main>
    <div>
        <h2 style="text-align: center;"> ${restaurant.name} </h2>
        <div style="display: flex; max-width: 700px; margin: auto;">
            <img class="label_img_fixed" src=${restaurant_label}/>
            <div class="restaurant_menu">
                <p class="p_menu">Меню на сегодня</p>
                <div style="margin-top: 12px">
                    <c:forEach items="${dishes}" var="dish">
                        <div class="p_dish"> ${dish.name} ${dish.price}</div>
                    </c:forEach></div>

                <c:if test="${isAdmin}">
                    <c:url value="/restaurants/${restaurant.id}/edit" var="edit"/>
                    <form action="${edit}" method="get">
                        <button type="submit">редактировать</button>
                    </form>
                    <form>
                        <button type="submit"> скрыть ресторан</button>
                    </form>
                </c:if>

            </div>
        </div>
    </div>
    <div style="text-align: center"> ${restaurant.description}</div>
</main>
<footer>
    <jsp:include page="fragments/bottom.jsp"/>
</footer>
</body>
</html>
