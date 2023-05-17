package com.university.dao.sql;

import com.university.dao.sql.config.TestConfig;
import com.university.model.Audience;
import com.university.model.Group;
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
class GroupDAOTest {

    @Autowired
    private GroupDAO groupDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Test
    void getAll_should_pass() {
        List<Group> expected = new ArrayList<>();
        expected.add(new Group(1, 1823));
        expected.add(new Group(2, 1785));
        expected.add(new Group(3, 5631));
        expected.add(new Group(4, 2541));
        assertThat(expected).isEqualTo(groupDAO.getAll());
    }

    @Test
    void create_should_pass() {
        Group source = new Group(100, 1234);
        groupDAO.create(source);
        assertThat(source).isEqualTo(groupDAO.get(100));
    }

    @Test
    void get() {
        Group expected = new Group(1, 1823);
        assertThat(expected).isEqualTo(groupDAO.get(1));
    }

    @Test
    void update() {
        Group source = new Group(1, 1822);
        groupDAO.update(source, 1);
        assertThat(source).isEqualTo(groupDAO.get(1));
    }

    @Test
    void delete() {
        int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups") - 1;
        groupDAO.delete(1);
        assertThat(expectedRows).isEqualTo(JdbcTestUtils.countRowsInTable(jdbcTemplate, "students"));
    }

    @Test
    void getAll_limit() {
        List<Group> expected = new ArrayList<>();
        expected.add(new Group(4,2541));
        assertThat(expected).isEqualTo(groupDAO.getAll(1, 3));
    }

    @Test
    void count_all() {
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "groups")).isEqualTo(groupDAO.countAll());
    }
}