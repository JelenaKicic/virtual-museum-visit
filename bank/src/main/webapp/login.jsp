<%--
  Created by IntelliJ IDEA.
  User: jelena.kicic
  Date: 13. 6. 2022.
  Time: 05:48
  To change this template use File | Settings | File Templates.
--%>
<%@page import="beans.*"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <title>Bank</title>
    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
    <link href="styles/styles.css" type="text/css" rel="stylesheet">
    <script src="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"></script>
</head>
<body>
<div class="content mdc-top-app-bar--fixed-adjust">
<form class="form-register" method="POST" action="?action=login">
    <div>
        <label class="mdc-text-field mdc-text-field--filled">
            <span class="mdc-text-field__ripple"></span>
            <span class="mdc-floating-label" id="username-label">Number</span>
            <input class="mdc-text-field__input" type="text" name="number" id="number" aria-labelledby="username-label">
            <span class="mdc-line-ripple"></span>
        </label>
    </div>

    <div>
        <label class="mdc-text-field mdc-text-field--filled">
            <span class="mdc-text-field__ripple"></span>
            <span class="mdc-floating-label" id="password-label">Pin</span>
            <input class="mdc-text-field__input" type="password" name="pin" id="pin" aria-labelledby="password-label">
            <span class="mdc-line-ripple"></span>
        </label>
    </div>

    <h4 class="mdc-typography-styles-headline4"><%=session.getAttribute("errorMessage") != null ? session.getAttribute("errorMessage").toString() : ""%></h4>
    <input class="mdc-button mdc-button--raised" type="submit" name="login" value="LogIn">
</form>
</div>
</body>
</html>
