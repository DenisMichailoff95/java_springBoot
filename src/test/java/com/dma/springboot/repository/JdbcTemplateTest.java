package com.dma.springboot.repository;

import com.dma.springboot.IntegrationTestBase;
import com.dma.springboot.entity.CompanyEntity;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcTemplateTest extends IntegrationTestBase {

    private static final String INSERT_SQL = "insert into company (name) values (?)";
    private static final String DELETE_RETURNING_SQL = "DELETE FROM company WHERE name = ? RETURNING *";

    @Autowired
    JdbcOperations jdbcOperations;

    @Test
    void TestInsert(){
        int result = jdbcOperations.update(INSERT_SQL, "Microsoft");
        Assertions.assertEquals(1, result);
    }


    @Test
    void TestReturning(){
        String companyName = "Microsoft";
        jdbcOperations.update(INSERT_SQL, companyName);
        List<CompanyEntity> result = jdbcOperations.query(
                DELETE_RETURNING_SQL,
                new RowMapper<CompanyEntity>(){
                  @Override
                    public CompanyEntity mapRow(ResultSet rs, int rowNum)
                      throws SQLException {
                          return CompanyEntity.builder()
                                  .id(rs.getInt("id"))
                                  .name(rs.getString("name"))
                                  .build();
                      };
                },
                companyName);
        MatcherAssert.assertThat(result, IsCollectionWithSize.hasSize(1));
//        MatcherAssert.assertThat(result, Matchers.hasSize(1));
    }

}
