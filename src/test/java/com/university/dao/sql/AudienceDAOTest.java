package com.university.dao.sql;

import com.university.dao.sql.config.TestConfig;
import com.university.model.Audience;
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
class AudienceDAOTest {

    @Autowired
    private AudienceDAO audienceDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getAll() {
        List<Audience> expected = new ArrayList<>();
        expected.add(new Audience(1, 1, 1105));
        expected.add(new Audience(2, 1, 2223));
        expected.add(new Audience(3, 1, 2310));
        expected.add(new Audience(4, 2, 3121));
        assertThat(expected).isEqualTo(audienceDAO.getAll());
    }

    @Test
    void create() {
        Audience source = new Audience(10, 2, 2910);
        audienceDAO.create(source);
        assertThat(source).isEqualTo(audienceDAO.get(10));
    }

    @Test
    void get() {
        Audience expected = new Audience(1, 1, 1105);
        assertThat(expected).isEqualTo(audienceDAO.get(1));
    }

    @Test
    void update() {
        Audience source = new Audience(1, 1, 1106);
        audienceDAO.update(source, 1);
        assertThat(source).isEqualTo(audienceDAO.get(1));
    }

    @Test
    void delete() {
        int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "audiences") - 1;
        audienceDAO.delete(1);
        assertThat(expectedRows).isEqualTo(JdbcTestUtils.countRowsInTable(jdbcTemplate, "audiences"));
    }

    @Test
    void getAll_limit() {
        List<Audience> expected = new ArrayList<>();
        expected.add(new Audience(4, 2, 3121));
        assertThat(expected).isEqualTo(audienceDAO.getAll(1, 3));
    }

    @Test
    void count_all() {
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "audiences")).isEqualTo(audienceDAO.countAll());
    }
}