package controllers;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.CardBean;

@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();

        String address = "/WEB-INF/login.jsp";
        session.setAttribute("errorMessage", "");

        if (action.equals("login")) {
            String number = request.getParameter("number");
            String pin = request.getParameter("pin");
            CardBean cardBean = CardBean.getCardBean(number, pin);

            if (cardBean != null) {
                cardBean.setLoggedIn(true);
                session.setAttribute("cardBean", cardBean);

                address = "/WEB-INF/cardManagement.jsp";
            } else {
                session.setAttribute("errorMessage", "Card number or pin are invalid");
            }

        } else if (action.equals("payments")) {
            CardBean cardBean = (CardBean)session.getAttribute("cardBean");

            if(cardBean != null && cardBean.isLoggedIn()) {
                cardBean.setOnlinePaymentEnabled(!cardBean.isOnlinePaymentEnabled());

                address = "/WEB-INF/cardManagement.jsp";
            }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}

