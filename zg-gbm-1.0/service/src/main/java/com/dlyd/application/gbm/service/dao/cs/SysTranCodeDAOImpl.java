package com.dlyd.application.gbm.service.dao.cs;

import com.dlyd.application.gbm.service.dao.SysTranCodeDAO;
import com.dlyd.application.gbm.service.domain.SysTranCodeDO;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.utils.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SysTranCodeDAOImpl implements SysTranCodeDAO {

  private static final RowMapper<SysTranCodeDO> RM = new RowMapper<SysTranCodeDO>() {
    @Override
    public SysTranCodeDO mapRow(ResultSet resultSet, int i) throws SQLException {
      SysTranCodeDO bean = new SysTranCodeDO();
      bean.setSourceType(JdbcUtils.getString(resultSet, "SOURCE_TYPE", ""));
      bean.setMessageType(JdbcUtils.getString(resultSet, "MESSAGE_TYPE", ""));
      bean.setMessageCode(JdbcUtils.getString(resultSet, "MESSAGE_CODE", ""));
      bean.setTranCode(JdbcUtils.getString(resultSet, "TRAN_CODE", ""));
      bean.setDescription(JdbcUtils.getString(resultSet, "DESCRIPTION", ""));
      return bean;
    }
  };


  private final JdbcTemplate jdbcTemplate;

  public SysTranCodeDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public boolean insert(final SysTranCodeDO sysTranCodeDO) {
    String sql = "insert into SYS_TRAN_CODE (SOURCE_TYPE, MESSAGE_TYPE, MESSAGE_CODE, TRAN_CODE, DESCRIPTION) values ( ? , ? , ? , ? , ? )";
    return jdbcTemplate.update(sql, new PreparedStatementSetter() {
      @Override
      public void setValues(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, sysTranCodeDO.getSourceType());
        preparedStatement.setString(2, sysTranCodeDO.getMessageType());
        preparedStatement.setString(3, sysTranCodeDO.getMessageCode());
        preparedStatement.setString(4, sysTranCodeDO.getTranCode());
        preparedStatement.setString(5, sysTranCodeDO.getDescription());
      }
    }) > 0;
  }


  @Override
  public SysTranCodeDO select(String sourceType, String messageType, String messageCode) {
    String sql = "select * from SYS_TRAN_CODE where 1=1   and SOURCE_TYPE = ?  and MESSAGE_TYPE = ?  and MESSAGE_CODE = ? ";
    try {
      return jdbcTemplate.queryForObject(sql, RM, sourceType, messageType, messageCode);
    } catch (EmptyResultDataAccessException e) {
      return new SysTranCodeDO().setTranCode("SI:ERROR").setDescription("交易不存在");
    } catch (DataAccessException e) {
      AppLog.logger.error("系统数据库操作未知异常", e);
      throw e;
    }
  }


}
