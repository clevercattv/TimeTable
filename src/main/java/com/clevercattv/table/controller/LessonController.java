package com.clevercattv.table.controller;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.dao.LessonDao;
import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dao.TeacherDao;
import com.clevercattv.table.dto.CrudLessonDTO;
import com.clevercattv.table.exception.ModifyDatabaseException;
import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.Room;
import com.clevercattv.table.model.Teacher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PSQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;

@WebServlet(name = "Lesson", urlPatterns = "/lesson")
public class LessonController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger(LessonController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getParameter("fName") == null) {
                req.setAttribute("response",
                        new CrudLessonDTO().toDto(
                                LessonDao.getInstance().findAll(),
                                TeacherDao.getInstance().findAllIdAndName(),
                                RoomDao.getInstance().findAllIdAndName(),
                                GroupDao.getInstance().findAllIdAndName()
                        ));
            } else {
                req.setAttribute("response",
                        new CrudLessonDTO().toDto(
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
            req.getRequestDispatcher("/Error500.jsp")
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LessonDao.getInstance().save(
                    new Lesson()
                            .setName(req.getParameter("name"))
                            .setGroup(new Group(Integer.parseInt(req.getParameter("group"))))
                            .setRoom(new Room(Integer.parseInt(req.getParameter("room"))))
                            .setTeacher(new Teacher(Integer.parseInt(req.getParameter("teacher"))))
                            .setDay(DayOfWeek.valueOf(req.getParameter("day")))
                            .setNumber(Lesson.Number.valueOf(req.getParameter("number")))
            );
        } catch (NamingException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, e.getMessage());
            throw e;
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Lesson on this time already exist!");
            throw new ModifyDatabaseException();
        } catch (NumberFormatException e){
            LOGGER.error(e);
            throw new IllegalArgumentException();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LessonDao.getInstance().update(
                    new Lesson()
                            .setId(Integer.parseInt(req.getParameter("id")))
                            .setName(req.getParameter("name"))
                            .setGroup(new Group(Integer.parseInt(req.getParameter("group"))))
                            .setRoom(new Room(Integer.parseInt(req.getParameter("room"))))
                            .setTeacher(new Teacher(Integer.parseInt(req.getParameter("teacher"))))
                            .setDay(DayOfWeek.valueOf(req.getParameter("day")))
                            .setNumber(Lesson.Number.valueOf(req.getParameter("number")))
            );
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Room name already used!");
            throw new ModifyDatabaseException();
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Can't parse id from request. Please refresh page and try again.");
            throw new IllegalArgumentException();
        } catch (NamingException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, e.getMessage());
            throw e;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LessonDao.getInstance()
                    .delete(Integer.parseInt(req.getParameter("id")));
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "You can't delete this lesson before it used in " +
                    ((PSQLException) e).getServerErrorMessage().getTable());
            throw new ModifyDatabaseException();
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Can't parse id from request. Please refresh page and try again.");
            throw e;
        }
    }

}