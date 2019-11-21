package com.clevercattv.table.controller;

import com.clevercattv.table.dao.LessonDao;
import com.clevercattv.table.dto.TimeTableLessonDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TimeTable", urlPatterns = "/timetable")
public class TimeTableController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(TimeTableController.class);
    private static final ObjectWriter WRITER = new ObjectMapper().writer();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<TimeTableLessonDTO> list = new ArrayList<>();
            LessonDao.getInstance().findAll().forEach(e -> list.add(new TimeTableLessonDTO().toDto(e)));
            req.setAttribute("timeTable", WRITER.writeValueAsString(list));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/TimeTable.jsp");
            requestDispatcher.forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            LOGGER.error(e);
            req.getRequestDispatcher("/Error500.jsp").forward(req, resp);
        }
    }

}