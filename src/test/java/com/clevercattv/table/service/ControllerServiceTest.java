package com.clevercattv.table.service;

import com.clevercattv.table.dao.GroupDao;
import com.clevercattv.table.database.TableService;
import com.clevercattv.table.model.Group;
import com.clevercattv.table.model.Lesson;
import com.clevercattv.table.model.TimeTable;
import com.clevercattv.table.serialize.TimeTableJsonSerializer;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

public class ControllerServiceTest {

    private static URL TEST_JSON_URL = Thread.currentThread()
            .getContextClassLoader()
            .getResource("TestBase.json");

    @Spy
    private ControllerService service = ControllerService.getInstance();
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private static List<Group> GROUPS;

    @BeforeClass
    public static void beforeClass() throws SQLException, IOException {
        TableService.dropTables();
        TableService.createTables();
        GROUPS = new ArrayList<>(GroupDao.getInstance().saveAll(
                TimeTableJsonSerializer.deserialize(TEST_JSON_URL)
                        .getLessons()
                        .stream()
                        .map(Lesson::getGroup)
                        .collect(Collectors.toList())
        ));
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = ClassCastException.class)
    public void testCast() throws IOException {
        when(request.getReader()).thenReturn(new BufferedReader(new FileReader(TEST_JSON_URL.getPath())));
        service.doPost(request,response, GroupDao.getInstance(), TimeTable.class);
        given(request.getAttribute("request")).willReturn(GROUPS);
    }

}