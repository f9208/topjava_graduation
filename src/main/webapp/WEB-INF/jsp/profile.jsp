<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="fragments/headTags.jsp"/>

<body>
<jsp:include page="fragments/head.jsp"/>
<main>
    <div class="container">
        <div class="col text-center h4 py-5">${updated ? 'Профайл был обновлен' : 'Обновление профайла' } </div>
        <div class="row justify-content-center">
            <div class="col-3">
                <div class="row">
                    <p class="col-12 h5">Результаты предыдущих голосований: </p>

                    <c:forEach items="${votes}" var="vote">
                        <div class="col-4"> ${vote.day}:</div>
                        <div class="col-7 offset-1"> ${vote.restaurant.name} </div>
                        <br>
                    </c:forEach></div>
            </div>
            <%--@elvariable id="userTo" type="ru.f9208.choicerestaurant.model.entities.to.UserTo"--%>
            <div class="col-8">
                <form:form cssClass="row" modelAttribute="userTo" method="post" action="profile">
                    <input name="updated" value="${updated=true}" type="hidden">
                    <div class="row">
                        <label class="col-2 pt-2" for="name">Name</label>
                        <form:input cssClass="my-2 col-5 form-control-sm" path="name"/>
                        <form:errors cssClass="col" path="name"/>
                    </div>
                    <div class="row">
                        <label class="col-2 pt-2" for="email"> Email</label>
                        <form:input cssClass="my-2 col-5 form-control-sm" path="email"/>
                        <form:errors cssClass="col" path="email"/>
                    </div>
                    <div class="row">
                        <label class="col-2 pt-2" for="password">Password</label>
                        <form:password cssClass="my-2 col-5 form-control-sm" path="password"/>
                        <form:errors cssClass="col" path="password"/>
                    </div>
                    <div class="row pt-2">
                        <button class="col-5 offset-2 btn-outline-success btn" type="submit"> send</button>
                    </div>
                </form:form>
            </div>

        </div>
    </div>
</main>
<footer>
    <jsp:include page="fragments/footer.jsp"/>
</footer>
</body>
</html>
