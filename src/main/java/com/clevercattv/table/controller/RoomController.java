package com.clevercattv.table.controller;

import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dto.CrudRoomDTO;
import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PSQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Room", urlPatterns = "/room")
public class RoomController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(RoomController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("response", new CrudRoomDTO(RoomDao.getInstance().findAll()));
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/CRUD_Room.jsp");
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try {
            RoomDao.getInstance().save(new Room()
                    .setName(req.getParameter("name"))
                    .setType(req.getParameter("type"))
            );
            req.setAttribute("complete", req.getParameter("name") + " successfully added!");
        } catch (NamingException e) {
            req.setAttribute(Controller.ERROR, e.getMessage());
            LOGGER.error(e);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")){
                req.setAttribute(Controller.ERROR, "Cannot add room becouse " +
                        ((PSQLException) e).getServerErrorMessage().getDetail());
            }
            LOGGER.error(e);
        } catch (IllegalArgumentException e) {
            req.setAttribute(Controller.ERROR, "Select type of room!");
            LOGGER.error(e);
        } finally {
            doGet(req, resp);
        }
    }

}