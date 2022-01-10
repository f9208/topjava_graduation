<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sprin" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="fragments/headTags.jsp"/>
<body>
<jsp:include page="fragments/head.jsp"/>
<main>
    <div class="container">

        <section class="row">
            <div class="col h3 text-black text-center pt-4 "> ${registered ? 'Поздрвляем, вы успешно зарегистрировались в системе! теперь войдите! ':' Войдите тут'}</div>
            <%--@elvariable id="userTo" type="ru.f9208.choicerestaurant.model.entities.to.UserTo"--%>
            <form:form cssClass="row text-center" method="post" action="spring_security_check" modelAttribute="userTo">
                <div>
                    <input class="col-3 my-2 form-control-sm" type="text" placeholder="Email" name="username">
                </div>
                <div>
                    <input class="col-3 my-2 form-control-sm" type="password" placeholder="Password" name="password">
                </div>
                <div>
                    <button class="col-3 my-2 btn-outline-success btn" type="submit">Войти</button>
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
