<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:set value="${requestScope['org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping']}"
       var="parentUrl"/>

<%--@elvariable id="dish" type="ru.f9208.choiserestaurant.model.entities.Dish"--%>
<c:url var="add" value="/restaurants/${restaurant.id}/add"/>
<form:form modelAttribute="dish" action="${add}" method="post">
    <label>dish name: </label>
    <form:input path="name" value=""/> <form:errors path="name"/>
    <form:input path="price" value=""/> <form:errors path="price"/>
    <button type="submit"> добавить блюдо</button>
    <input type="hidden" name="parentUrl" value="${parentUrl}">
</form:form>
</body>
</html>
