package com.clevercattv.table.controller;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.dao.LessonDao;
import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dao.TeacherDao;
import com.clevercattv.table.dto.TimeTableModelsDTO;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.service.ControllerService;
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

@WebServlet(name = "Lesson", urlPatterns = "/lesson")
public class LessonController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(LessonController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("fName") == null) {
                req.setAttribute("response",
                        new TimeTableModelsDTO().toDto(
                                LessonDao.getInstance().findAll(),
                                TeacherDao.getInstance().findAllIdAndName(),
                                RoomDao.getInstance().findAllIdAndName(),
                                GroupDao.getInstance().findAllIdAndName()
                        ));
            } else {
                req.setAttribute("response",
                        new TimeTableModelsDTO().toDto(
                                LessonDao.getInstance().findFilteredByRequest(req),
                                TeacherDao.getInstance().findAllIdAndName(),
                                RoomDao.getInstance().findAllIdAndName(),
                                GroupDao.getInstance().findAllIdAndName()
                        ));
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/CRUD_Lesson.jsp");
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            LOGGER.error(e);
            req.getRequestDispatcher(ControllerService.ERROR_500).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ControllerService.getInstance()
                    .doPost(req, resp, LessonDao.getInstance(), Lesson.class);
        } catch (IOException e) {
            LOGGER.error(e);
            req.getRequestDispatcher(ControllerService.ERROR_500).forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ControllerService.getInstance()
                    .doPut(req, resp, LessonDao.getInstance(), Lesson.class);
        } catch (IOException e) {
            LOGGER.error(e);
            req.getRequestDispatcher(ControllerService.ERROR_500).forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ControllerService.getInstance()
                    .doDelete(req, resp, LessonDao.getInstance(), Lesson.class);
        } catch (IOException e) {
            LOGGER.error(e);
            req.getRequestDispatcher(ControllerService.ERROR_500).forward(req, resp);
        }
    }

}