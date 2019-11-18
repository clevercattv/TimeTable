package com.clevercattv.table.controller;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.exception.ModifyDatabaseException;
import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.model.Group;
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

@WebServlet(name = "Group", urlPatterns = "/group")
public class GroupController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger(GroupController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String filterName = req.getParameter("fName");
            if (Strings.isEmpty(filterName)) {
                req.setAttribute("response", GroupDao.getInstance().findAll());
            } else {
                req.setAttribute("response", GroupDao.getInstance().findByName(filterName));
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/CRUD_Group.jsp");
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
            GroupDao.getInstance().save(new Group()
                    .setName(req.getParameter("name"))
            );
        } catch (NamingException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, e.getMessage());
            throw e;
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Group with this name already exist!");
            throw new ModifyDatabaseException();
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Select type of group!");
            throw e;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            GroupDao.getInstance().update(new Group()
                    .setId(Integer.parseInt(req.getParameter("id")))
                    .setName(req.getParameter("name"))
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
            GroupDao.getInstance().delete(Integer.parseInt(req.getParameter("id")));
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "You can't delete this group before it used in " +
                    ((PSQLException) e).getServerErrorMessage().getTable());
            throw new ModifyDatabaseException();
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Can't parse id from request. Please refresh page and try again.");
            throw new IllegalArgumentException();
        }
    }

}