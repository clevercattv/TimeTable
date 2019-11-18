package com.clevercattv.table.controller;

import com.clevercattv.table.dao.TeacherDao;
import com.clevercattv.table.dto.CrudTeacherDTO;
import com.clevercattv.table.exception.ModifyDatabaseException;
import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.model.Teacher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.postgresql.util.PSQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Teacher", urlPatterns = "/teacher")
public class TeacherController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger(TeacherController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String filterName = req.getParameter("fName");
            if (Strings.isEmpty(filterName)){
                req.setAttribute("response", new CrudTeacherDTO(TeacherDao.getInstance().findAll()));
            } else {
                req.setAttribute("response", new CrudTeacherDTO(TeacherDao.getInstance()
                        .findByNameAndType(filterName,req.getParameter("fType"))));
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/CRUD_Teacher.jsp");
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
            TeacherDao.getInstance().save(new Teacher()
                    .setFullName(req.getParameter("name"))
                    .setType(req.getParameter("type"))
            );
        } catch (NamingException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, e.getMessage());
            throw e;
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Teacher with this name already exist!");
            throw new ModifyDatabaseException();
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Select type of teacher!");
            throw e;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            TeacherDao.getInstance().update(new Teacher()
                    .setId(Integer.parseInt(req.getParameter("id")))
                    .setFullName(req.getParameter("name"))
                    .setType(req.getParameter("type"))
            );
//            req.setAttribute("complete", req.getParameter("name") + " successfully updated!");
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR,"Teacher name already used!");
            throw new ModifyDatabaseException();
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR,"Can't parse id from request. Please refresh page and try again.");
            throw new IllegalArgumentException();
        } catch (NamingException e){
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR,e.getMessage());
            throw e;
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        try {
            TeacherDao.getInstance().delete(Integer.parseInt(req.getParameter("id")));
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "You can't delete this teacher before it used in " +
                    ((PSQLException) e).getServerErrorMessage().getTable());
            throw new ModifyDatabaseException();
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Can't parse id from request. Please refresh page and try again.");
            throw new IllegalArgumentException();
        }
    }

}