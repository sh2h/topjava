<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals List</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h1>My meals!</h1>
<table style="margin: 5px;">
    <tr >
        <th  >№</th>
        <th >Дата/время</th>
        <th  >Описание</th>
        <th  >Калории</th>
        <th  ></th>
        <th  ></th>
    </tr>
    <c:forEach items="${meals}" var="mealTo" varStatus="mealNumber" >
        <tr style="color:${mealTo.excess ? 'red' : 'green'}">
            <fmt:parseDate value="${mealTo.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDate"/>
            <td >${mealNumber.count}</td>
            <td ><fmt:formatDate value="${parseDate}" pattern="dd-MM-yy HH-mm"/></td>
            <td >${mealTo.description}</td>
            <td >${mealTo.calories}</td>
            <td ><a  href=<c:url value="meals?id=${mealTo.id}"/>>Редактировать</a></td>
            <td ><a  href=<c:url value="meals?action=delete&id=${mealTo.id}"/>>Удалить</a></td>
        </tr>
    </c:forEach>
</table>


<div style="margin: 50px 5px;">
<form method="post" action="meals">
    <input type="hidden" name="id"  value="${meal.id}"/>
    Date: <label><input type="datetime-local"  name="dateTime" value="${meal.dateTime}"></label><br>
    Description: <label><input type="text" name="description" value="${meal.description}"></label><br>
    Calories: <label><input type="number" name="calories" value="${meal.calories}"></label><br>
    <input type="submit" value="${meal.id<=0?"Добавить":"Изменить"}">
</form>
</div>

</body>
</html>
