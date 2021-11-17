<!DOCTYPE html>
<html lang="ru" class="h-100">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="fragments/headTags.jsp"/>

<body>
<jsp:include page="fragments/head.jsp"/>
<main>
    <div class="container">
        <section class="row">
            <div class="col h3 text-black text-center pt-4">Регистрация</div>
            <%--@elvariable id="userTo" type="ru.f9208.choiserestaurant.model.entities.to.UserTo"--%>
            <form:form cssClass="row row-cols-1 justify-content-center" modelAttribute="userTo" method="post"
                       action="register">
            <div class="row justify-content-center">
                <div class="col-1 text-center pt-2"><label for="name">Name</label></div>
                <div class="col-2"><form:input cssClass="my-2 form-control-sm" path="name"/> <form:errors
                        path="name"/></div>
            </div>
            <div class="row justify-content-center">
                <div class="col-1 text-center pt-2"><label for="email"> Email</label></div>
                <div class="col-2"><form:input cssClass="my-2 form-control-sm" path="email"/> <form:errors
                        path="email"/></div>
            </div>
            <div class="row justify-content-center">
                <div class="col-1 text-center pt-2"><label> password</label></div>
                <div class="col-2"><form:password cssClass="my-2 form-control-sm" path="password"/> <form:errors
                        path="password"/></div>
            </div>
            <div class="row justify-content-center pt-2">
                <div class=" col-1"></div>
                <div class=" col-2">
                    <button class="btn btn-outline-primary" style="width: 177px" type="submit"> send</button>
                </div>
                </form:form>
        </section>
    </div>
</main>
<footer>
    <jsp:include page="fragments/footer.jsp"/>
</footer>
</body>
</html>