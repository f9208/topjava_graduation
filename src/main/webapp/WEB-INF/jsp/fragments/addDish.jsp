<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--<jsp:include page="headTags.jsp"/>--%>
<c:set value="${requestScope['org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping']}"
       var="parentUrl"/>

<%--@elvariable id="dish" type="ru.f9208.choiserestaurant.model.entities.Dish"--%>
<c:url var="add" value="/restaurants/${restaurant.id}/add"/>

    <div class="row justify-content-center">
        <div class="col-6">
            <form:form modelAttribute="dish" action="${add}" method="post">
                <div class="row">
                    <label class="col-4 offset-2 form-control-sm ">Название блюда</label>
                    <label class="col-2 ms-2 text-end form-control-sm">Цена</label>
                </div>
                <div class="row ">
                    <form:input cssClass="col-4 offset-2 form-control-sm " path="name" value=""/>
                    <form:input cssClass="col-2 ms-2 text-end form-control-sm" path="price" value=""/>
                    <button class="col-3 ms-2 btn btn-outline-success" type="submit"> добавить блюдо</button>
                </div>
                <div class="row">
                   <div class="col-4 offset-2 form-control-sm text-danger"> <form:errors path="name"/> </div>
                   <div class="col-2 ms-2 text-end form-control-sm text-danger"> <form:errors path="price"/> </div>
                </div>
                <input type="hidden" name="parentUrl" value="${parentUrl}">
            </form:form>
        </div>
</div>

