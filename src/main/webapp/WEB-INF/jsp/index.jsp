<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<jsp:include page="fragments/headTags.jsp"/>
<head>
    <script src="resources/js/index.js"></script>
</head>
<body>
<jsp:include page="fragments/head.jsp"/>
<main>
    <div class="container">
        <div class="row py-3 text-center">
            <div class="col-6 mx-auto">
                <p class="h1 fw-light">Голосовалка</p>
                <p class="lead pt-2">Посмотрите описание и меню представленных ресторанов </p>
            </div>
        </div>
        <sec:authorize access="hasRole('ADMIN')" var="isAdmin"/>
        <div class="row pt-2 justify-content-center">
            <div class="col-8">
                <div class="row row-cols-1 row-cols-md-3 g-3">
                    <c:forEach items="${restaurants}" var="restaurant">
                        <c:if test="${restaurant.enabled==true || isAdmin==true}">
                            <c:url value="${restaurant.label.linkReduced}" var="restaurant_label"/>
                            <div class="col">
                                <jsp:useBean id="restaurant3"
                                             class="ru.f9208.choicerestaurant.model.entities.Restaurant"/>
                                <h5 id="name">${restaurant.name}</h5>
                                <a href="restaurants/${restaurant.id}">
                                    <img class="col-11 g-3" src="${restaurant_label}">
                                </a>
                                <div class="card-body" id="description">
                                    <p class="card-text" style=""> ${restaurant.description} </p>
                                    <div class="row ">
                                        <div class="btn-group col-5" id="vote">
                                            голосов: ${restaurant.vote.size()}</div>
                                        <div class="col-2 text-danger" id="disabled">
                                            <c:if test="${restaurant.enabled==false}" var="enabled">
                                                скрыт
                                            </c:if></div>
                                        <a class="btn btn-sm btn-outline-secondary col-3 offset-1"
                                           href="restaurants/${restaurant.id}#showMenu">меню</a>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <%--    <div class="main" typeof="hidden" style="height: 200px;">--%>
    <%--        &lt;%&ndash;        Некоторый контент...&ndash;%&gt;--%>
    <%--    </div>--%>
    <%--    <!-- ... -->--%>
    <%--    <script src="jquery.dotdotdot.min.js"></script>--%>
    <%--    <script>--%>
    <%--        $(function () {--%>
    <%--            $('.main').dotdotdot();--%>
    <%--        });--%>
    <%--    </script>--%>
<footer>
    <jsp:include page="fragments/footer.jsp"/>
</footer>
</body>
</html>
