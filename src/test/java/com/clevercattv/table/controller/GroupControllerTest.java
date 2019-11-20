package com.clevercattv.table.controller;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.database.TableService;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(DataProviderRunner.class)
public class GroupControllerTest extends MockitoJUnit {

    private static URL TEST_JSON_URL = Thread.currentThread()
            .getContextClassLoader()
            .getResource("TestBase.json");

    @Spy
    private GroupController controller = new GroupController();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    RequestDispatcher requestDispatcher = mock(RequestDispatcher.class);

    @Before
    public void setUp() throws SQLException, IOException {
        MockitoAnnotations.initMocks(this);
        TableService.dropTables();
        TableService.createTables();
        GroupDao.getInstance().saveAll(
                TimeTableJsonSerializer.deserialize(TEST_JSON_URL)
                        .getLessons()
                        .stream()
                        .map(Lesson::getGroup)
                        .collect(Collectors.toList())
        );
    }

    @DataProvider
    public static Object[][] dataProvider() throws SQLException {
        return new Object[][]{
                {null, GroupDao.getInstance().findAll()},
                {"509", GroupDao.getInstance().findByName("509")}
        };
    }

    @Test
    @UseDataProvider("dataProvider")
    public void doGet(String name, List<Group> groups) throws ServletException, IOException {
        when(request.getParameter("fName")).thenReturn(name);
        when(request.getRequestDispatcher(eq("/CRUD_Group.jsp"))).thenReturn(requestDispatcher);
        controller.doGet(request, response);
        verify(request,atLeast(1)).getParameter("response");
        assertEquals(request.getAttribute("request"),groups);
//        HttpServletRequest request = mock(HttpServletRequest.class);
//        HttpServletResponse response = mock(HttpServletResponse.class);
//        when(request.getParameter("password")).thenReturn("secret");
//        verify(request, atLeast(1)).getParameter("username"); // only if you want to verify username was called...
    }

    @Test
    public void doPost() {

    }

    @Test
    public void doPut() {

    }

    @Test
    public void doDelete() {

    }
}