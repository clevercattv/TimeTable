package com.clevercattv.table.controller;

import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.exception.ModifyDatabaseException;
import com.clevercattv.table.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PSQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

@WebServlet(name = "RoomDelete", urlPatterns = "/room/delete")
public class RoomDeleteController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(RoomDeleteController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)  {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            RoomDao.getInstance().delete(new Room().setId(id));
        } catch (SQLException e) {
            LOGGER.error(e);
            if (e.getSQLState().equals("23503")){
                req.setAttribute(Controller.ERROR,"You can't delete this room before it used in " +
                        ((PSQLException) e).getServerErrorMessage().getTable());
                throw new ModifyDatabaseException();
            }
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR,"Can't parse id from request. Please refresh page and try again.");
            throw new IllegalArgumentException();
        }
    }

}
