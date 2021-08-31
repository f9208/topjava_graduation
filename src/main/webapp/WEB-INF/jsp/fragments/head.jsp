<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <main class="header">
        <div class="headerbutton">
            <sec:authorize access="isAnonymous()">
                <form:form cssClass="headForm" method="post" action="spring_security_check">
                    <input class="form-control mr-1" type="text" placeholder="Email" name="username">
                    <input class="form-control mr-1" type="password" placeholder="Password" name="password">
                    <button type="submit"> Войти</button>
                </form:form>
                <a href="register">
                    <button class="headForm" type="submit"> Зарегистрироваться</button>
                </a>
            </sec:authorize>
            <form:form action="logout" method="post"> <sec:authorize access="isAuthenticated()">
                Здравствуйте,
                <a href="profile"><sec:authentication property="principal.userTo.name"/>!</a>
                <button type="submit">Выйти</button>
            </sec:authorize></form:form>
        </div>
        <div><a href="/choicerestaurant"> <img src="resources/pic/theater.png" class="headerimg"></a></div>
    </main>
</head>

