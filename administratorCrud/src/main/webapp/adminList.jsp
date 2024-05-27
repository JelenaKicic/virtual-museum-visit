<%@ page import="administration.Administrator" %><%--
  Created by IntelliJ IDEA.
  User: jelena.kicic
  Date: 6. 4. 2022.
  Time: 21:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="administratorBean" class="administration.AdministratorBean" scope="request"></jsp:useBean>
<html>
<head>
    <title>Admin List</title>
    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
    <link href="styles/styles.css" type="text/css" rel="stylesheet">
    <script src="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"></script>
</head>
<body>
<%
    if(session.getAttribute("authToken") == null) {
        if (request.getParameter("token") != null) {
            Administrator admin = administratorBean.getUserByToken(request.getParameter("token"));
            if (admin == null) {
                response.sendRedirect("unauthorised.jsp");
            } else {
                session.setAttribute("authToken", request.getParameter("token"));
            }
        } else {
            response.sendRedirect("unauthorised.jsp");
        }
    }

    if(request.getParameter("delete") != null) {
        administratorBean.delete(request.getParameter("delete"));
    } else if (request.getParameter("update") != null) {
        response.sendRedirect("updateAdmin.jsp?user_id=" + request.getParameter("update"));
    }
%>
<%@include file="WEB-INF/navbar.jsp"%>
<div class="content mdc-top-app-bar--fixed-adjust">
    <div class="mdc-data-table">
        <div class="mdc-data-table__table-container">
            <table class="mdc-data-table__table" aria-label="Dessert calories">
                <tbody class="mdc-data-table__content">
                <%
                    for(Administrator admin : administratorBean.getAllAdministrators()) {
                        out.write("<tr class=\"mdc-data-table__row\">");
                        out.write("<td class=\"mdc-data-table__cell\" scope=\"row\">" + admin.getUsername() + "</td>");
                        out.write(
                                "<td class=\"mdc-data-table__cell\" scope=\"row\"> <form method=\"post\">" +
                                        "<input class=\"mdc-button mdc-button--outlined\" type=\"submit\" value=\"Update\">" +
                                        "<input type=\"hidden\" name=\"update\" value=\"" + admin.getId() + "\"/>" +
                                        "</form></td>"
                        );
                        out.write(
                                "<td class=\"mdc-data-table__cell\" scope=\"row\"> <form method=\"post\">" +
                                        "<input class=\"mdc-button mdc-button--outlined\" type=\"submit\" value=\"Delete\">" +
                                        "<input type=\"hidden\" name=\"delete\" value=\"" + admin.getId() + "\"/>" +
                                        "</form></td>"
                        );
                        out.write("</tr>");
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
