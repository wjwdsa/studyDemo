package com.dlyd.application.gbm.service.dao.cs;


import com.dlyd.application.gbm.service.dao.SysXtcsDAO;
import com.dlyd.application.gbm.service.domain.SysXtcsDO;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.utils.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SysXtcsDAOImpl implements SysXtcsDAO {


  private static final RowMapper<SysXtcsDO> RM = new RowMapper<SysXtcsDO>() {
    @Override
    public SysXtcsDO mapRow(ResultSet resultSet, int i) throws SQLException {
      SysXtcsDO bean = new SysXtcsDO();
      bean.setXtbz(JdbcUtils.getString(resultSet, "XTBZ", ""));
      bean.setXtmc(JdbcUtils.getString(resultSet, "XTMC", ""));
      bean.setXtjc(JdbcUtils.getString(resultSet, "XTJC", ""));
      bean.setXtrq(JdbcUtils.getString(resultSet, "XTRQ", ""));
      bean.setLsh(JdbcUtils.getInt(resultSet, "LSH"));
      bean.setXtzt(JdbcUtils.getInt(resultSet, "XTZT"));
      return bean;
    }
  };


  private final JdbcTemplate jdbcTemplate;

  public SysXtcsDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


  @Override
  public boolean insert(final SysXtcsDO sysXtcsDO) {
    String sql = "insert into SYS_XTCS        (XTBZ, XTMC, XTJC, XTRQ, LSH, XTZT)        values        ( ? , ? , ? , ? , ? , ? )";
    return jdbcTemplate.update(sql, new PreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, sysXtcsDO.getXtbz());
        preparedStatement.setString(2, sysXtcsDO.getXtmc());
        preparedStatement.setString(3, sysXtcsDO.getXtjc());
        preparedStatement.setString(4, sysXtcsDO.getXtrq());
        preparedStatement.setInt(5, sysXtcsDO.getLsh());
        preparedStatement.setInt(6, sysXtcsDO.getXtzt());
      }
    }) > 0;
  }

  @Override
  public boolean delete(String xtbz) {
    String sql = "delete from SYS_XTCS XTBZ = ? ";
    return jdbcTemplate.update(sql, xtbz) > 0;
  }

  @Override
  public boolean update(String xtbz, final SysXtcsDO sysXtcsDO) {
    String sql = " update SYS_XTCS set   XTMC = ? ,    XTJC = ? ,    XTRQ = ? ,    LSH = ? ,    XTZT = ?    where 1 = 1 and XTBZ = ?";
    return jdbcTemplate.update(sql, new PreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, sysXtcsDO.getXtmc());
        preparedStatement.setString(2, sysXtcsDO.getXtjc());
        preparedStatement.setString(3, sysXtcsDO.getXtrq());
        preparedStatement.setInt(4, sysXtcsDO.getLsh());
        preparedStatement.setInt(5, sysXtcsDO.getXtzt());
        preparedStatement.setString(6, xtbz);
      }
    }) > 0;
  }

  @Override
  public boolean updateXtrq(String xtbz, final String xtrq) {
    String sql = " update SYS_XTCS set   XTRQ = ?  where XTBZ = ?";
    return jdbcTemplate.update(sql, new PreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, xtrq);
        preparedStatement.setString(2, xtbz);
      }
    }) > 0;
  }

  @Override
  public SysXtcsDO select(String xtbz) throws DataAccessException {
    String sql = "select * from SYS_XTCS where 1=1   and XTBZ = ? ";
    try {
      return jdbcTemplate.queryForObject(sql, RM, xtbz);
    } catch (EmptyResultDataAccessException e) {
      return null;
    } catch (DataAccessException e) {
      AppLog.logger.error("系统数据库操作未知异常", e);
      throw e;
    }
  }

}
