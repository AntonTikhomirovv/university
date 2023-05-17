package com.university.dao.sql;

import com.university.dao.sql.config.TestConfig;
import com.university.model.Group;
import com.university.model.Student;
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

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(SpringExtension.class)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
})
class StudentDAOTest {

    @Autowired
    private StudentDAO studentDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getAll_should_pass() {
        List<Student> expected = new ArrayList<>();
        expected.add(new Student(1, "Rees", "Best", "rb@gmail.com", 1, 1));
        expected.add(new Student(2, "Matthias ", "Riggs", "mr@gmail.com", 1, 1));
        expected.add(new Student(3, "Brady ", "Mccartney", "bm@gmail.com", 1, 1));
        expected.add(new Student(4, "Inara ", "Krueger", "ik@gmail.com", 2, 2));
        expected.add(new Student(5, "Benjamin ", "Cameron", "bk@gmail.com", 3, 3));
        expected.add(new Student(6, "Kealan ", "Charlton", "kc@gmail.com", 4, 4));
        assertThat(expected).isEqualTo(studentDAO.getAll());
    }

    @Test
    void get_should_pass() {
        Student expected = new Student(3, "Brady ", "Mccartney", "bm@gmail.com", 1, 1);
        assertThat(expected).isEqualTo(studentDAO.get(3));
    }

    @Test
    void create_should_pass() {
        Student source = new Student(1000, "Anton", "Tikhomirov", "at@gmail.com", 4, 4);
        studentDAO.create(source);
        assertThat(source).isEqualTo(studentDAO.get(1000));
    }

    @Test
    void update_should_pass() {
        Student source = new Student(1, "Phillip", "Johns", "pj@gmail.com", 1, 1);
        studentDAO.update(source, 1);
        assertThat(source).isEqualTo(studentDAO.get(1));
    }

    @Test
    void delete_should_pass() {
        int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "students") - 1;
        studentDAO.delete(1);
        assertThat(expectedRows).isEqualTo(JdbcTestUtils.countRowsInTable(jdbcTemplate, "students"));
    }

    @Test
    void getAll_limit() {
        List<Student> expected = new ArrayList<>();
        expected.add(new Student(6, "Kealan", "Charlton", "kc@gmail.com", 4, 4));
        assertThat(expected).isEqualTo(studentDAO.getAll(1, 5));
    }

    @Test
    void count_all() {
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "students")).isEqualTo(studentDAO.countAll());
    }
}