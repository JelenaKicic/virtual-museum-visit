<%@ page import="administration.Administrator" %><%--
  Created by IntelliJ IDEA.
  User: jelena.kicic
  Date: 7. 4. 2022.
  Time: 01:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="administratorBean" class="administration.AdministratorBean" scope="request"></jsp:useBean>

<html>
<head>
    <title>Title</title>
    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
    <link href="styles/styles.css" type="text/css" rel="stylesheet">
    <script src="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"></script>
</head>
<body>
<%
    if(session.getAttribute("authToken") == null) {
        response.sendRedirect("unauthorised.jsp");
    } else {
        if (request.getParameter("user_id") != null) {
            session.setAttribute("updateErrorMessage", "");
            Administrator admin = administratorBean.getUserById(request.getParameter("user_id"));
            if (admin != null) {
                session.setAttribute("oldUsername", admin.getUsername());
                session.setAttribute("update_user_id", request.getParameter("user_id"));
            } else {
                response.sendRedirect("adminList.jsp");
            }
        } else {
            response.sendRedirect("adminList.jsp");
        }
    }

    if (request.getParameter("update") != null) {
        if(request.getParameter("username") != null && request.getParameter("username") != "" && session.getAttribute("update_user_id") != null) {
            if (administratorBean.isUserNameAllowed(request.getParameter("username"))) {
                if(administratorBean.update(request.getParameter("username"), session.getAttribute("update_user_id").toString())) {
                    session.setAttribute("updateErrorMessage", "");
                    session.setAttribute("update_user_id", "");
                } else {
                    session.setAttribute("updateErrorMessage", "There's been issue, try again!");
                }
            } else {
                session.setAttribute("updateErrorMessage", "Username is taken!");
            }
        } else {
            session.setAttribute("updateErrorMessage", "Username is required!");
        }
    } else {
        session.setAttribute("updateErrorMessage", "");
    }
%>
<%@include file="WEB-INF/navbar.jsp"%>
<div class="content mdc-top-app-bar--fixed-adjust">
    <form method="POST" action="updateAdmin.jsp">
        <div>
            <label class="mdc-text-field mdc-text-field--filled">
                <span class="mdc-text-field__ripple"></span>
                <span class="mdc-floating-label" id="username-label">Username</span>
                <input class="mdc-text-field__input" type="text" name="username" aria-labelledby="username-label" value="<%=session.getAttribute("oldUsername")%>">
                <span class="mdc-line-ripple"></span>
            </label>
        </div>

        <h4 class="mdc-typography-styles-headline4"><%=session.getAttribute("updateErrorMessage").toString()%></h4>

        <input class="mdc-button mdc-button--raised" type="submit" name="update" value="update">
        <a href="adminList.jsp">
            <button class="mdc-button mdc-button--outlined">
                <span class="mdc-button__ripple"></span>
                <span class="mdc-button__label">Cancel</span>
            </button>
        </a>
    </form>
</div>
</body>
</html>
