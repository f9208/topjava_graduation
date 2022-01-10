<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<header>
    <div class="container">
        <c:url value="/spring_security_check" var="signin"/>
        <c:url value="/register" var="signup"/>
        <c:url value="/logout" var="logout"/>
        <c:url value="/profile" var="profile"/>
        <c:url value="/" var="root"/>


        <sec:authorize access="isAnonymous()">
            <form:form cssClass="row" method="post" action="${signin}">
                <div class="row justify-content-between py-1">
                    <img class="col-1" src="resources/pic/shop.svg" width="24" height="24">
                    <div class="col-6  text-end">
                        <input class="col-2 form-control-sm mx-1 mt-1" type="text" placeholder="Email"
                               name="username">
                        <input class="col-2 form-control-sm mx-1  mt-1" type="password" placeholder="Password"
                               name="password">
                        <button class="col-2 btn btn-outline-dark btn-sm mx-1" type="submit">Войти</button>
                        <a class="col-4 btn btn-outline-dark btn-sm mx-1" href="${signup}"> Зарегистрироваться</a>
                    </div>
                </div>
            </form:form>

        </sec:authorize>
        <form:form action="${logout}" method="post" cssClass="row offset-5"> <sec:authorize access="isAuthenticated()">
            <div class="col offset-2 pt-2">Здравствуйте,
                <a href="${profile}"><sec:authentication property="principal.userTo.name"/>!</a>
            </div>
            <button class="col-4 btn btn-outline-dark" type="submit">Выйти</button>
        </sec:authorize></form:form>

    </div>
    <div class="card-img-top opacity-75"><a href="${root}">
        <img class="img-fluid" src=
        <c:url value="/resources/pic/theater.JPG"/>></a></div>
    </div>
</header>