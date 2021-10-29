<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <main class="header">
        <c:url value="/spring_security_check" var="signin"/>
        <c:url value="/register" var="signup"/>
        <c:url value="/logout" var="logout"/>
        <c:url value="/profile" var="profile"/>

        <div class="headerbutton">
            <sec:authorize access="isAnonymous()">
                <form:form cssClass="headForm" method="post" action="${signin}">
                    <input class="form-control mr-1" type="text" placeholder="Email" name="username">
                    <input class="form-control mr-1" type="password" placeholder="Password" name="password">
                    <button type="submit"> Войти</button>
                </form:form>
                <a href="${signup}">
                    <button class="headForm" type="submit"> Зарегистрироваться</button>
                </a>
            </sec:authorize>
            <form:form action="${logout}" method="post"> <sec:authorize access="isAuthenticated()">
                Здравствуйте,
                <a href="${profile}"><sec:authentication property="principal.userTo.name"/>!</a>
                <button type="submit">Выйти</button>
            </sec:authorize></form:form>
        </div>
        <div><a href="/choicerestaurant"> <img src=<c:url value="/resources/pic/theater.png"/> class="headerimg"></a></div>
    </main>
</head>

