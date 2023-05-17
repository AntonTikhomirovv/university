package com.university.dao.sql;

import com.university.dao.sql.config.TestConfig;
import com.university.model.Subject;
import com.university.model.Teacher;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(SpringExtension.class)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
})
class TeacherDAOTest {

    @Autowired
    private TeacherDAO teacherDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getAll() {
        List<Teacher> expected = new ArrayList<>();
        expected.add(new Teacher(1, "Alanah", "Peck", "ap@gmail.com"));
        expected.add(new Teacher(2, "Zaki", "Weaver", "zw@gmail.com"));
        expected.add(new Teacher(3, "Phillip", "Weber", "pw@gmail.com"));
        expected.add(new Teacher(4, "Beverley", "Forbes", "bf@gmail.com"));
        assertThat(expected).isEqualTo(teacherDAO.getAll());
    }

    @Test
    void create() {
        Teacher source = new Teacher(100, "Anton", "Tikhomirov,", "at@gmail.com");
        teacherDAO.create(source);
        assertThat(source).isEqualTo(teacherDAO.get(100));
    }

    @Test
    void get() {
        Teacher expected = new Teacher(1, "Alanah", "Peck", "ap@gmail.com");
        assertThat(expected).isEqualTo(teacherDAO.get(1));
    }

    @Test
    void update() {
        Teacher source = new Teacher(1, "Anton", "Tikhomirov,", "at@gmail.com");
        teacherDAO.update(source, 1);
        assertThat(source).isEqualTo(teacherDAO.get(1));
    }

    @Test
    void delete() {
        int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers") - 1;
        teacherDAO.delete(1);
        Assertions.assertThat(expectedRows).isEqualTo(JdbcTestUtils.countRowsInTable(jdbcTemplate, "teachers"));
    }

    @Test
    void getAll_limit() {
        List<Teacher> expected = new ArrayList<>();
        expected.add(new Teacher(4, "Beverley", "Forbes", "bf@gmail.com"));
        Assertions.assertThat(expected).isEqualTo(teacherDAO.getAll(1, 3));
    }

    @Test
    void count_all() {
        Assertions.assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects")).isEqualTo(teacherDAO.countAll());
    }
}