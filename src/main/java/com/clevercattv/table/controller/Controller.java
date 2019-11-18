package com.clevercattv.table.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class Controller extends HttpServlet {

    protected static final String ERROR = "response";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equals("GET")) {
            doGet(req, resp);
        } else {
            String method = req.getParameter("method");
            switch (method) {
                case "POST":
                    doPost(req, resp);
                    break;
                case "PUT":
                    doPut(req, resp);
                    break;
                case "DELETE":
                    doDelete(req, resp);
                    break;
                default:
                    super.service(req, resp);
                    break;
            }
        }
    }

}
