<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<head>
    <meta charset="UTF-8">
    <title>Не можете выбрать в какой кабак пойти? мы поможем!</title>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
</head>

<body>
<header>
    <jsp:include page="fragments/head.jsp"/>
</header>
<main>
    <section><c:forEach items="${restaurants}" var="restaurant">
        <jsp:useBean id="restaurant" class="ru.f9208.choiserestaurant.model.entities.Restaurant"/>
        <%--        <div class="restaurants_list">--%>
        <div class="restaurant_item" style="background-image: url('${restaurant.label}');">
            <div> ${restaurant.name} </div>
                <%--            </div>--%>
                <%--            <div class="restaurant_item">--%>
                <%--                <img src="resources/pic/blueberry_on_table.JPG" class="restaurant_img">--%>
                <%--            </div>--%>
        </div>
    </c:forEach>

    </section>
</main>
<footer>
    <jsp:include page="fragments/bottom.jsp"/>
</footer>
</body>
</html>
