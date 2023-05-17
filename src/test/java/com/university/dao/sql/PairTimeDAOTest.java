package com.university.dao.sql;

import com.university.dao.sql.config.TestConfig;
import com.university.model.Group;
import com.university.model.PairTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = TestConfig.class)
@ExtendWith(SpringExtension.class)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:schema.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:data.sql")
})
class PairTimeDAOTest {

    @Autowired
    private PairTimeDAO pairTimeDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void getAll() {
        List<PairTime> expected = new ArrayList<>();
        expected.add(new PairTime(1, LocalTime.parse("09:00:00"), LocalTime.parse("10:30:00")));
        expected.add(new PairTime(2, LocalTime.parse("10:40:00"), LocalTime.parse("12:10:00")));
        expected.add(new PairTime(3, LocalTime.parse("12:20:00"), LocalTime.parse("13:50:00")));
        expected.add(new PairTime(4, LocalTime.parse("14:10:00"), LocalTime.parse("15:40:00")));
        expected.add(new PairTime(5, LocalTime.parse("15:50:00"), LocalTime.parse("17:20:00")));
        assertThat(expected).isEqualTo(pairTimeDAO.getAll());
    }


    @Test
    void create() {
        PairTime source = new PairTime(6, LocalTime.parse("17:30:00"), LocalTime.parse("20:00:00"));
        pairTimeDAO.create(source);
        assertThat(source).isEqualTo(pairTimeDAO.get(6));
    }

    @Test
    void get() {
        PairTime expected = new PairTime(1, LocalTime.parse("09:00:00"), LocalTime.parse("10:30:00"));
        assertThat(expected).isEqualTo(pairTimeDAO.get(1));
    }

    @Test
    void update() {
        PairTime source = new PairTime(1,  LocalTime.parse("09:30:00"), LocalTime.parse("11:00:00"));
        pairTimeDAO.update(source, 1);
        assertThat(source).isEqualTo(pairTimeDAO.get(1));
    }

    @Test
    void delete() {
        int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "pair_times") - 1;
        pairTimeDAO.delete(1);
        assertThat(expectedRows).isEqualTo(JdbcTestUtils.countRowsInTable(jdbcTemplate, "pair_times"));
    }


    @Test
    void count_all() {
        assertThat(JdbcTestUtils.countRowsInTable(jdbcTemplate, "pair_times")).isEqualTo(pairTimeDAO.countAll());
    }
}