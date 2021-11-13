<!DOCTYPE html>
<html lang="ru">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<head>
    <meta charset="UTF-8">
    <title>Не можете выбрать в какой кабак пойти? мы поможем!</title>
    <link rel="stylesheet" type="text/css" href=
    <c:url value="/resources/css/styles.css"/>>
</head>

<body>
<sec:authorize access="isAuthenticated()" var="isAdmin"/>
<c:set value="${restaurant}" var="restaurant"/>
<c:url value="${restaurant.label.linkReduced}" var="label"/>
<c:url value="/restaurants/${restaurant.id}/edit" var="editRestaurant"/>
<c:set value="${errors}" var="errors"/>

<header>
    <jsp:include page="fragments/head.jsp"/>
</header>
<c:if test="${isAdmin}">
    <div><h2 style="text-align: center;"> ${restaurant.name}</h2></div>
    <div><img class="label_img_fixed" src="${label}"/></div>
    <%--@elvariable id="restaurant" type="ru.f9208.choiserestaurant.model.entities.Restaurant"--%>
    <form:form modelAttribute="restaurant" method="post" action="${editRestaurant}"
               cssStyle="margin-left: auto;margin-right: auto;width: 1400px;" enctype="multipart/form-data">

        <table><c:forEach items="${restaurant.menu}" var="dish" varStatus="status">
            <tr>
                <td><input name="menu[${status.index}].name" value="${dish.name}"></td>
                <td><input name="menu[${status.index}].price" value="${dish.price}"></td>
                <td><c:if
                        test="${errors[(dish.id)]==true}">необходимо ввести уникальное название и цену продукта!</c:if></td>
                <input type="hidden" name="menu[${status.index}].id" value="${dish.id}"/>
            </tr>
        </c:forEach>
        </table>
        <table>
            <tr>
                <td><label for="name">Name</label></td>
                <td><form:input path="name"/> <form:errors path="name"/></td>
            </tr>
            <tr>
                <td><label for="description">description: </label></td>
                <td><form:input path="description"/></td>
            </tr>
            <tr>
                <td><form:checkbox path="enabled"/></td>
                <td> доступность для голосования</td>
            </tr>
            <tr>
                <td><label>сменить картинку: </label>
                    <input type="file" name="inputFile" accept=".jpg, .jpeg, .png"><br/>
                </td>
            </tr>
            <tr>
                <td>
                    <button type="submit">сохранить изменения</button>
                </td>
            </tr>
        </table>
        <form:input type="hidden" path="label.name"/>
        <form:input type="hidden" path="label.id"/>
        <form:input type="hidden" path="label.linkOrigin"/>
        <form:input type="hidden" path="label.linkReduced"/>

    </form:form>
    <jsp:include page="fragments/addDish.jsp"/>

</c:if>

<footer>
    <jsp:include page="fragments/bottom.jsp"/>
</footer>
</body>
</html>
