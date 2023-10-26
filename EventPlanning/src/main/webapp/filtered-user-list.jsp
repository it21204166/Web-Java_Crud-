<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>Filtered User List</title>
    <!-- Add your CSS and other dependencies here -->
</head>
<body>
    <h1>Filtered User List</h1>

    <table class="table table-striped">
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Business Type</th>
                <th>Email</th>
                <th>Phone Number</th>
                <th>About</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.name}" /></td>
                    <td><c:out value="${user.businesstype}" /></td>
                    <td><c:out value="${user.email}" /></td>
                    <td><c:out value="${user.phoneno}" /></td>
                    <td><c:out value="${user.about}" /></td>
                    <td>
                        <a href="<%=request.getContextPath()%>/edit?id=<c:out value="${user.id}" />" class="btn btn-primary">Edit</a>
                        <a href="<%=request.getContextPath()%>/delete?id=<c:out value="${user.id}" />" class="btn btn-danger">Delete</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
