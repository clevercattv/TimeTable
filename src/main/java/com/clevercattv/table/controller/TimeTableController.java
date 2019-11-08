package com.clevercattv.table.controller;

import com.clevercattv.table.dao.LessonDao;
import com.clevercattv.table.dto.TimeTableDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "TimeTable", urlPatterns = "/timetable")
public class TimeTableController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(TimeTableController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            TimeTableDTO timeTableDTO = new TimeTableDTO();
            req.setAttribute("timeTable",timeTableDTO.toDto(LessonDao.getInstance().findAll()));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/FormTest.jsp");
            requestDispatcher.forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (PrintWriter writer = resp.getWriter()) {
            String name = req.getParameter("name");
            writer.println("Welcome " + name);
        } catch (IOException e) {
            LOGGER.error(e);
        }
    }

}