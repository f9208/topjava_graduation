<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sprin" uri="http://java.sun.com/jsp/jstl/core" %>

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

    <section> ${registered ? 'поздрвляю, вы успешно зарегистрировались в системе! теперь войдите! ':' войдите тут'}</section>
    <section>
        <%--@elvariable id="userTo" type="ru.f9208.choiserestaurant.model.entities.to.UserTo"--%>
        <form:form method="post" action="spring_security_check" modelAttribute="userTo">
            <input class="form-control mr-1" type="text" placeholder="Email" name="username">
            <input class="form-control mr-1" type="password" placeholder="Password" name="password">
            <button type="submit">Войти</button>
        </form:form>

    </section>
</main>
<footer>
    <jsp:include page="fragments/bottom.jsp"/>
</footer>
</body>
</html>
