<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="fragments/headTags.jsp"/>

<body>
<jsp:include page="fragments/head.jsp"/>

<sec:authorize access="isAuthenticated()" var="isAdmin"/>
<c:set value="${restaurant}" var="restaurant"/>
<c:url value="${restaurant.label.linkReduced}" var="label"/>
<c:url value="/restaurants/${restaurant.id}/edit" var="editRestaurant"/>
<c:set value="${errors}" var="errors"/>


<main>
    <div class="container">
        <c:if test="${isAdmin}">

        <div class="row">
            <div class="h2  text text-center col-12"> ${restaurant.name}</div>
        </div>

            <%--@elvariable id="restaurant" type="ru.f9208.choiserestaurant.model.entities.Restaurant"--%>

        <form:form cssClass="row justify-content-center" modelAttribute="restaurant" method="post"
                   action="${editRestaurant}"
                   enctype="multipart/form-data">
        <div class="row">
            <div class="col-4 offset-1 mt-5"><img class="img-fluid" src="${label}"/></div>
            <div class="col-7">
                <label class="row justify-content-center h5" for="description">Описание </label>
                <form:textarea cssClass="row offset-1 form-control-sm" cols="80" rows="5" path="description"/>
                <div class="row py-3">
                    <label class="col-2 offset-1 h5" for="name">Название:</label>
                    <form:input cssClass="col-4 form-control-sm" path="name"/>
                    <form:errors cssClass="col-5" path="name"/>
                </div>

                <div class="row py-3">
                    <p class="col-4 h6 offset-1">Доступность для голосования</p>  <form:checkbox
                        cssClass="col-1 text-center" path="enabled"/>
                </div>
                <div class="row py-3">
                    <label class="col-3 offset-1 h6">Сменить картинку: </label>
                    <input class="col-5" type="file" name="inputFile" accept=".jpg, .jpeg, .png">
                </div>
                <div class="pt-4">
                    <button class="offset-3 btn btn-outline-primary col-5" type="submit">сохранить изменения</button>
                </div>
            </div>


            <form:input type="hidden" path="label.name"/>
            <form:input type="hidden" path="label.id"/>
            <form:input type="hidden" path="label.linkOrigin"/>
            <form:input type="hidden" path="label.linkReduced"/>
        </div>

        <div class="pt-2"></div>
        <div class="row py-4">
            <div class="col">
                <hr>
            </div>
            <div class="col-auto h4">Меню</div>
            <div class="col">
                <hr>
            </div>
        </div>
        <div class="col-6">
            <c:forEach items="${restaurant.menu}" var="dish" varStatus="status">
                <c:if test="${errors[(dish.id)]==true}">
                    <div class="row ry-1">
                        <div class="col-9 offset-1 text-danger"> необходимо ввести уникальное название и цену
                            продукта!
                        </div>
                    </div>
                </c:if>
                <div class="row py-1">
                    <input class="col-4 offset-2 form-control-sm " name="menu[${status.index}].name"
                           value="${dish.name}">
                    <input class="col-2 ms-2 text-end form-control-sm" align="right" name="menu[${status.index}].price"
                           value="${dish.price}"> &#160;&#8381;

                    <input type="hidden" name="menu[${status.index}].id" value="${dish.id}"/>
                </div>
            </c:forEach>
        </div>
        </form:form>
        <div class="row py-4">
            <div class="col">
                <hr>
            </div>
            <div class="col-auto h4">Добавить новое блюдо</div>
            <div class="col">
                <hr>
            </div>
        </div>
            <jsp:include page="fragments/addDish.jsp"/>

        </c:if>

</main>
<footer>
    <jsp:include page="fragments/footer.jsp"/>
</footer>
</body>
</html>
