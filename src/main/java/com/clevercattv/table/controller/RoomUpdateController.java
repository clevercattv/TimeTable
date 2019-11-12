package com.clevercattv.table.controller;

import com.clevercattv.table.dao.RoomDao;
import com.clevercattv.table.exception.ModifyDatabaseException;
import com.clevercattv.table.exception.NamingException;
import com.clevercattv.table.model.Room;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "RoomUpdate", urlPatterns = "/room/update")
public class RoomUpdateController extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(RoomUpdateController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            RoomDao.getInstance().update(new Room()
                    .setId(Integer.parseInt(req.getParameter("id")))
                    .setName(req.getParameter("name"))
                    .setType(req.getParameter("type"))
            );
            req.setAttribute("complete", req.getParameter("name") + " successfully updated!");
        } catch (SQLException e) {
            LOGGER.error(e);
            if (e.getSQLState().equals("23505")){
                req.setAttribute(Controller.ERROR,"Room name already used!");
                throw new ModifyDatabaseException();
            }
            resp.sendError(999);
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR,"Can't parse id from request. Please refresh page and try again.");
            throw new IllegalArgumentException();
        } catch (NamingException e){
            LOGGER.error(e);
            req.setAttribute(Controller.ERROR,e.getMessage());
            throw new NamingException();
        }
    }

}
