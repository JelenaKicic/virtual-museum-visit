<%--
  Created by IntelliJ IDEA.
  User: jelena.kicic
  Date: 4. 4. 2022.
  Time: 22:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="administratorBean" class="administration.AdministratorBean" scope="request"></jsp:useBean>
<html>
<head>
    <title>Admin registration</title>
    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
    <link href="styles/styles.css" type="text/css" rel="stylesheet">
    <script src="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"></script>
</head>
<body>
<%
    if(session.getAttribute("authToken") == null) {
        response.sendRedirect("unauthorised.jsp");
    }

    session.setAttribute("errorMessage", "");
    if(request.getParameter("submit") != null) {
        if (request.getParameter("username") != null && request.getParameter("password") != null && request.getParameter("username") != "" && request.getParameter("password") != "") {
            if (administratorBean.isUserNameAllowed(request.getParameter("username"))) {
                if(administratorBean.create(request.getParameter("username"), request.getParameter("password"))) {
                    session.setAttribute("errorMessage", "");
                    response.sendRedirect("adminList.jsp");
                } else {
                    session.setAttribute("errorMessage", "There's been issue, try again!");
                }
            } else {
                session.setAttribute("errorMessage", "Username is taken!");
            }
        } else {
            session.setAttribute("errorMessage", "Password and username are required!");
        }
    } else {
        session.setAttribute("errorMessage", "");
    }
%>
<%@include file="WEB-INF/navbar.jsp"%>
<div class="content mdc-top-app-bar--fixed-adjust">
    <form method="POST" action="createAdmin.jsp">
        <div>
            <label class="mdc-text-field mdc-text-field--filled">
                <span class="mdc-text-field__ripple"></span>
                <span class="mdc-floating-label" id="username-label">Username</span>
                <input class="mdc-text-field__input" type="text" name="username" aria-labelledby="username-label">
                <span class="mdc-line-ripple"></span>
            </label>
        </div>

        <div>
            <label class="mdc-text-field mdc-text-field--filled">
                <span class="mdc-text-field__ripple"></span>
                <span class="mdc-floating-label" id="password-label">Password</span>
                <input class="mdc-text-field__input" type="password" name="password" aria-labelledby="password-label">
                <span class="mdc-line-ripple"></span>
            </label>
        </div>

        <h4 class="mdc-typography-styles-headline4"><%=session.getAttribute("errorMessage").toString()%></h4>
        <input class="mdc-button mdc-button--raised" type="submit" name="submit" value="create">
    </form>
</div>
</body>
</html>
