package com.clevercattv.table.controller;

import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.dto.CrudRoomDTO;
import com.clevercattv.table.model.Room;
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

@WebServlet(name = "Room", urlPatterns = "/room")
public class RoomController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(RoomController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String filterName = req.getParameter("fName");
            if (filterName == null) {
                req.setAttribute("response", new CrudRoomDTO(RoomDao.getInstance().findAll()));
            } else {
                req.setAttribute("response", new CrudRoomDTO(RoomDao.getInstance()
                        .findByNameAndType(filterName, req.getParameter("fType"))));
            }
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/CRUD_Room.jsp");
            requestDispatcher.forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            LOGGER.error(e);
            req.getRequestDispatcher(ControllerService.ERROR_500)
                    .forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ControllerService.getInstance()
                    .doPost(req, resp, RoomDao.getInstance(), Room.class);
        } catch (IOException e) {
            LOGGER.error(e);
            req.getRequestDispatcher(ControllerService.ERROR_500).forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ControllerService.getInstance()
                    .doPut(req, resp, RoomDao.getInstance(), Room.class);
        } catch (IOException e) {
            LOGGER.error(e);
            req.getRequestDispatcher(ControllerService.ERROR_500).forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ControllerService.getInstance()
                    .doDelete(req, resp, RoomDao.getInstance(), Room.class);
        } catch (IOException e) {
            LOGGER.error(e);
            req.getRequestDispatcher(ControllerService.ERROR_500).forward(req, resp);
        }
    }

}