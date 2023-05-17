package com.university.dao.sql;

import com.university.dao.sql.config.TestConfig;
import com.university.model.Student;
import com.university.model.Subject;
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
class SubjectDAOTest {

    @Autowired
    private SubjectDAO subjectDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getAll() {
        List<Subject> expected = new ArrayList<>();
        expected.add(new Subject(1, "Math"));
        expected.add(new Subject(2, "History"));
        expected.add(new Subject(3, "Programming"));
        expected.add(new Subject(4, "Physics"));
        assertThat(expected).isEqualTo(subjectDAO.getAll());
    }

    @Test
    void create() {
        Subject source = new Subject(10, "test");
        subjectDAO.create(source);
        assertThat(source).isEqualTo(subjectDAO.get(10));
    }

    @Test
    void get() {
        Subject expected = new Subject(1, "Math");
        assertThat(expected).isEqualTo(subjectDAO.get(1));
    }

    @Test
    void update() {
        Subject source = new Subject(1, "High math");
        subjectDAO.update(source, 1);
        assertThat(source).isEqualTo(subjectDAO.get(1));
    }

    @Test
    void delete() {
        int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects") - 1;
        subjectDAO.delete(1);
        Assertions.assertThat(expectedRows).isEqualTo(JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects"));
    }

    @Test
    void getAll_limit() {
        List<Subject> expected = new ArrayList<>();
        expected.add(new Subject(4, "Physics"));
        Assertions.assertThat(expected).isEqualTo(subjectDAO.getAll(1, 3));
    }

    @Test
    void count_all() {
        Assertions.assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "subjects")).isEqualTo(subjectDAO.countAll());
    }
}