<%--
  Created by IntelliJ IDEA.
  User: jelena.kicic
  Date: 13. 6. 2022.
  Time: 05:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<%@ page import="beans.*"%>
<%@ page import="beans.TransactionBean" %>
<jsp:useBean id="cardBean" type="beans.CardBean" scope="session" />
<!DOCTYPE html>
<html>
<head>
    <title>Bank</title>
    <link href="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.css" rel="stylesheet">
    <link href="styles/styles.css" type="text/css" rel="stylesheet">
    <script src="https://unpkg.com/material-components-web@latest/dist/material-components-web.min.js"></script>
</head>
<div class="content mdc-top-app-bar--fixed-adjust">
    <div class="mdc-data-table">
        <div class="mdc-data-table__table-container">
            <table class="mdc-data-table__table" aria-label="Dessert calories">
                <tr class="mdc-data-table__header-row">
                    <th class="mdc-data-table__cell" scope="col">Id</th>
                    <th class="mdc-data-table__cell" scope="col">Amount</th>
                    <th class="mdc-data-table__cell" scope="col">Card id</th>
                </tr>
                <tbody class="mdc-data-table__content">

                    <%for (TransactionBean transactionBean : TransactionBean.getAllByCardId(cardBean.getId())) {%>
                        <tr class="mdc-data-table__row">
                            <td class="mdc-data-table__cell" class="mdc-data-table__cell">
                                <%out.print(transactionBean.getId());%>
                            </td>
                            <td class="mdc-data-table__cell" class="mdc-data-table__cell">
                                <%out.print(transactionBean.getAmount());%>
                            </td>
                            <td class="mdc-data-table__cell" class="mdc-data-table__cell">
                                <%out.print(transactionBean.getCard_id());%>
                            </td>
                        </tr>
                    <%}%>
            </tbody>
        </table>

        <a href="?action=payments">
            <button class="mdc-button mdc-button--raised" >
                <%
                    if (cardBean.isOnlinePaymentEnabled()) {
                        out.print("Disable online payments");
                    } else {
                        out.print("Enable online payments");
                    }
                %>
            </button>
        </a>

    </div>
</div>
</div>
</body>
</html>
