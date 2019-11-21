package com.clevercattv.table.service;

import com.clevercattv.table.dao.DaoImpl;
import com.clevercattv.table.model.EntityId;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PSQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

public class ControllerService {

    private static final Logger LOGGER = LogManager.getLogger(ControllerService.class);

    public static final String ERROR_500 = "/Error500.jsp";

    private static final String ERROR = "error";
    private static final String REQUEST_ERROR = "Server can't read request. Please refresh page and try again.";

    private static final ControllerService SERVICE = new ControllerService();

    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.configure(DeserializationFeature.WRAP_EXCEPTIONS, false);
    }

    private ControllerService() {
    }

    public static ControllerService getInstance() {
        return SERVICE;
    }

    public <D extends DaoImpl, C extends Class> void doPost(HttpServletRequest req, HttpServletResponse resp,
                                                            D dao, C clazz) throws IOException {
        try {
            dao.save(ControllerService.parseObject(req, clazz));
        } catch (JsonMappingException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, e.getCause().getMessage());
            resp.sendError(SC_BAD_REQUEST);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, clazz.getSimpleName() + " with this name already exist!");
            resp.sendError(SC_INTERNAL_SERVER_ERROR);
        } catch (IllegalArgumentException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, "Select type of " + clazz.getSimpleName().toLowerCase() + "!");
            resp.sendError(SC_BAD_REQUEST);
        } catch (IOException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, ControllerService.REQUEST_ERROR);
            resp.sendError(SC_INTERNAL_SERVER_ERROR);
        }
    }

    public <D extends DaoImpl, C extends Class> void doPut(HttpServletRequest req, HttpServletResponse resp,
                                                           D dao, C clazz) throws IOException {
        try {
            dao.update(ControllerService.parseObject(req, clazz));
        } catch (JsonMappingException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, e.getCause().getMessage());
            resp.sendError(SC_BAD_REQUEST);
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, clazz.getSimpleName() + " name already used!");
            resp.sendError(SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, "Can't parse id from request. Please refresh page and try again.");
            resp.sendError(SC_BAD_REQUEST);
        } catch (IOException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, ControllerService.REQUEST_ERROR);
            resp.sendError(SC_INTERNAL_SERVER_ERROR);
        }
    }

    public <D extends DaoImpl, C extends Class> void doDelete(HttpServletRequest req, HttpServletResponse resp,
                                                              D dao, C clazz) throws IOException {
        try {
            dao.delete((EntityId) ControllerService.parseObject(req, clazz));
        } catch (SQLException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, "You can't delete " + clazz.getSimpleName().toLowerCase() +
                    " because it used in " + ((PSQLException) e).getServerErrorMessage().getTable());
            resp.sendError(SC_INTERNAL_SERVER_ERROR);
        } catch (NumberFormatException | JsonMappingException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, "Can't parse id from request. Please refresh page and try again.");
            resp.sendError(SC_BAD_REQUEST);
        } catch (IOException e) {
            LOGGER.error(e);
            req.setAttribute(ERROR, ControllerService.REQUEST_ERROR);
            resp.sendError(SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static <T> T parseObject(HttpServletRequest req, Class<T> type) throws IOException {
        return MAPPER.readerFor(type)
                .readValue(
                        req.getReader()
                                .lines()
                                .collect(Collectors.joining())
                );
    }

}