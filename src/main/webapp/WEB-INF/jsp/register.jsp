<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <meta charset="UTF-8">
    <title>Не можете выбрать в какой кабак пойти? мы поможем!</title>
    <link rel="stylesheet" type="text/css" href="resources/css/styles.css">
</head>
<body>
<main>
    <jsp:include page="fragments/head.jsp"/>
    <h4> регистрация</h4>
    <div>
        <%--@elvariable id="userTo" type="ru.f9208.choiserestaurant.model.entities.to.UserTo"--%>
        <form:form modelAttribute="userTo" method="post" action="register">
            <table>
                <tr>
                    <td><label for="name">Name</label></td>
                    <td><form:input path="name"/> <form:errors path="name"/></td>
                </tr>
                <tr>
                    <td><label for="email"> Email</label></td>
                    <td><form:input path="email"/> <form:errors path="email"/></td>
                </tr>
                <tr>
                    <td><label> password</label></td>
                    <td><form:password path="password"/> <form:errors path="password"/></td>
                </tr>
                <tr>
                    <td>
                        <button type="submit"> send</button>
                    </td>
                </tr>
            </table>
        </form:form>
    </div>

</main>
<jsp:include page="fragments/bottom.jsp"/>
</body>
</html>