package com.clevercattv.table.controller;

import com.clevercattv.table.dao.LessonDao;
import com.clevercattv.table.dto.TimeTableDTO;
import com.clevercattv.table.model.TimeTableFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebServlet(name = "TimeTable", urlPatterns = "/timetable")
public class TimeTableController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(TimeTableController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TimeTableDTO timeTableDTO = new TimeTableDTO();
            req.setAttribute("filter", TimeTableFilter.build());
            timeTableDTO = timeTableDTO.toDto(LessonDao.getInstance().findAll());
            req.setAttribute("timeTable",timeTableDTO);
            req.setAttribute("timeTableJson",new ObjectMapper().writer().writeValueAsString(timeTableDTO));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/TimeTable.jsp");
            requestDispatcher.forward(req, resp);
        } catch (SQLException | ServletException | IOException e) {
            LOGGER.error(e);
            req.getRequestDispatcher("/Error500.jsp")
                    .forward(req, resp);
        }
    }

}