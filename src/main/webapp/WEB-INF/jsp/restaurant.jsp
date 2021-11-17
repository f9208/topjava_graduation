<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="page" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="fragments/headTags.jsp"/>

<body>
<jsp:include page="fragments/head.jsp"/>
<sec:authorize access="isAuthenticated()" var="isAuthenticated"/>
<sec:authorize access="hasRole('ADMIN')" var="isAdmin"/>
<sec:authorize access="isAnonymous()" var="isAnon"/>
<c:set value="${restaurant}" var="restaurant"/>
<c:url value="${restaurant.label.linkReduced}" var="restaurant_label"/>
<c:url value="/vote/restaurant/${restaurant.id}/" var="toVoteUrl"/>
<c:url value="/vote/restaurant/delete/${restaurant.id}/" var="deleteVoteUrl"/>

<main>
    <div class="container">
        <div class="row py-3">
            <div class="row-cols-1">
                <h3 class="col-5 py-2 gx-5 offset-3"> ${restaurant.name} </h3>
                <div class="row row-cols-2 offset-2">
                    <img class="col-3 g-3" src=${restaurant_label}/>
                    <div class="col-5 g-3 text-black-50 ms-5 mt-3"><p>${restaurant.description}</p>
                    </div>
                </div>
                <div class="col pt-4">
                    <p class="text-black text-center fs-3">Меню на сегодня</p>
                    <div class="row row-cols-2">
                        <c:forEach items="${restaurant.menu}" var="menu">
                            <div class="col-3 offset-4"> ${menu.name}</div>
                            <div class="col-3"><fmt:formatNumber type="currency"
                                                                 currencyCode="RUB">${menu.price}</fmt:formatNumber></div>
                        </c:forEach></div>
                    <br>
                    <hr class="my-3"/>
                    <p class="text-black text-center fs-3">голосов за ресторан: ${restaurant.vote.size()}</p>
                    <hr class="my-3"/>

                    <c:if test="${restaurant.enabled==true}">
                    <c:if test="${isAuthenticated==false}"><br>
                    <div class="text-center text-black-50">
                        <a class="btn-outline-dark btn" href="login">войдите
                        </a>
                        чтобы проголосовать за этот ресторан</c:if>
                    </div>
                    <c:if test="${isAuthenticated}">
                        <%--                уже проголосовал за этот--%>
                    <c:if test="${vote.restaurant.id==restaurant.id}">
                        <div class="text-center">
                            <p class="text-center" style="color:darkgoldenrod"> вы уже проголосовали за этот
                                ресторан!</p>
                            <form action="${deleteVoteUrl}" method="post">
                                <form:input type="hidden" path="vote.id"/>
                                <button class="btn btn-outline-danger">передумал, отозвать голос</button>
                            </form>
                        </div>
                    </c:if>
                        <%--                 уже проголосовал за другой--%>
                    <c:if test="${vote!=null && vote.restaurant.id!=restaurant.id}" var="statment">
                    <div><p class="text-center" style="color:#ff4c41">сегодня вы уже голосовали за другой ресторан</p>
                        <div class="row justify-content-center">
                            <form class="col-3 ms-2 text-end" action="${toVoteUrl}" method="post">
                                <form:input type="hidden" path="restaurant.id"/>
                                <button class="btn btn-outline-success">передумал, выбираю этот</button>
                            </form>
                            <form class="col-3" action="${deleteVoteUrl}" method="post">
                                <form:input type="hidden" path="vote.id"/>
                                <button class="btn btn-outline-danger">отозвать голос совсем</button>
                            </form>
                        </div>
                    </div>
                </div>
                </c:if>
                    <%--                 еще не голосовал сегодня вообще --%>
                <c:if test="${vote==null}" var="statment">
                    <div class="text-center pt-2">
                        <form action="${toVoteUrl}" method="post">
                            <form:input type="hidden" path="restaurant.id"/>
                            <button class="btn-outline-success btn">отдать свой голос за этот ресторан</button>
                        </form>
                    </div>
                </c:if>
                </c:if>
                </c:if>
                <br>
                <%--                панель администратора--%>
                <div>
                    <c:if test="${isAdmin}">
                        <div class="row">
                            <div class="col">
                                <hr>
                            </div>
                            <div class="col-auto">Панель администратора</div>
                            <div class="col">
                                <hr>
                            </div>
                        </div>
                        <div class="row justify-content-center">
                            <c:if test="${restaurant.enabled==true}">
                                <p class="col text-success text-end pt-1">доступен для голосования</p></c:if>
                            <c:if test="${restaurant.enabled==false}"><p class="col text-danger text-end pt-1">не
                                доступен для
                                голосования</p></c:if>
                            <c:url value="/restaurants/${restaurant.id}/edit" var="edit_descript"/>
                            <form class="col" action="${edit_descript}" method="get">
                                <button class="btn btn-outline-dark" type="submit">редактировать описание</button>
                            </form>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</main>
<footer>
    <jsp:include page="fragments/footer.jsp"/>
</footer>
</body>
</html>
