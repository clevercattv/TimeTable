package com.clevercattv.table.controller;

import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dto.CrudRoomDTO;
import com.clevercattv.table.exception.ModifyDatabaseException;
import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.model.Room;
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

@WebServlet(name = "Room", urlPatterns = "/room")
public class RoomController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger(RoomController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String filterName = req.getParameter("fName");
            if (filterName == null){
                req.setAttribute("response", new CrudRoomDTO(RoomDao.getInstance().findAll()));
            } else {
                req.setAttribute("response", new CrudRoomDTO(RoomDao.getInstance()
                        .findByNameAndType(filterName,req.getParameter("fType"))));
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/CRUD_Room.jsp");
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
            RoomDao.getInstance().save(new Room()
                    .setName(req.getParameter("name"))
                    .setType(req.getParameter("type"))
            );
        } catch (NamingException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, e.getMessage());
            throw e;
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Room with this name already exist!");
            throw new ModifyDatabaseException();
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Select type of room!");
            throw e;
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        try {
            RoomDao.getInstance().update(new Room()
                    .setId(Integer.parseInt(req.getParameter("id")))
                    .setName(req.getParameter("name"))
                    .setType(req.getParameter("type"))
            );
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR,"Room name already used!");
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
            RoomDao.getInstance().delete(Integer.parseInt(req.getParameter("id")));
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "You can't delete this room before it used in " +
                    ((PSQLException) e).getServerErrorMessage().getTable());
            throw new ModifyDatabaseException();
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR, "Can't parse id from request. Please refresh page and try again.");
            throw new IllegalArgumentException();
        }
    }

}