<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="page" uri="http://java.sun.com/jsp/jstl/core" %>
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

<sec:authorize access="isAuthenticated()" var="isAuthenticated"/>
<sec:authorize access="hasRole('ADMIN')" var="isAdmin"/>
<sec:authorize access="isAnonymous()" var="isAnon"/>
<c:set value="${restaurant}" var="restaurant"/>
<c:url value="${restaurant.label.linkReduced}" var="restaurant_label"/>
<c:url value="/vote/restaurant/${restaurant.id}/" var="toVoteUrl"/>
<c:url value="/vote/restaurant/delete/${restaurant.id}/" var="deleteVoteUrl"/>

<main>
    <div>
        <h2 style="text-align: center;"> ${restaurant.name} </h2>
        <div style="display: flex; max-width: 700px; margin: auto;">
            <img class="label_img_fixed" src=${restaurant_label}/>
            <div class="restaurant_menu">
                <p class="p_menu">Меню на сегодня</p>
                <div style="margin-top: 12px">
                    <c:forEach items="${restaurant.menu}" var="menu">
                        <div class="p_dish"> ${menu.name}
                            <fmt:formatNumber type="currency" currencyCode="RUB">${menu.price}</fmt:formatNumber>
                        </div>
                    </c:forEach></div>
                <div style="text-align: center"> ${restaurant.description}</div>
                <br>
                <div>
                    <c:if test="${isAdmin}">
                        <c:if test="${restaurant.enabled==true}"> доступен для голосования</c:if>
                        <c:if test="${restaurant.enabled==false}"> не доступен для голосования</c:if>
                        <c:url value="/restaurants/${restaurant.id}/edit" var="edit_descript"/>
                        <form action="${edit_descript}" method="get">
                            <br>
                            <button type="submit">редактировать описание</button>
                        </form>
                    </c:if>
                </div>
                голосов за этот ресторан: ${restaurant.vote.size()}
                <br>
                <br>
                <c:if test="${restaurant.enabled==true}">
                    <c:if test="${isAuthenticated==false}"> войдите, чтобы проголосовать за этот ресторан</c:if>
                    <c:if test="${isAuthenticated}">
                        <%--                уже проголосовал за этот--%>
                        <c:if test="${vote.restaurant.id==restaurant.id}">
                            вы проголосовали за этот ресторан!
                            <form action="${deleteVoteUrl}" method="post">
                                <form:input type="hidden" path="vote.id"/>
                                <button>передумал, отозвать голос</button>
                            </form>
                        </c:if>

                        <%--                 уже проголосовал за другой--%>
                        <c:if test="${vote!=null && vote.restaurant.id!=restaurant.id}" var="statment">
                            вы уже сегодня голосовали за другой ресторан
                            <form action="${toVoteUrl}" method="post">
                                <form:input type="hidden" path="restaurant.id"/>
                                <button>передумал, выбираю этот</button>
                            </form>
                        </c:if>
                        <%--                 еще не голосовал сегодня вообще --%>
                        <c:if test="${vote==null}" var="statment">
                            <form action="${toVoteUrl}" method="post">
                                <form:input type="hidden" path="restaurant.id"/>
                                <button>выбираю этот</button>
                            </form>
                        </c:if>
                    </c:if>
                </c:if>
                <br>
            </div>
        </div>
    </div>
    <br>
    <br>
</main>
<footer>
    <jsp:include page="fragments/bottom.jsp"/>
</footer>
</body>
</html>
