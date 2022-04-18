package com.dlyd.application.gbm.service.dao.cs;

import com.dlyd.application.gbm.service.DataException;
import com.dlyd.application.gbm.service.DataNotExistException;
import com.dlyd.application.gbm.service.dao.ReportingStatusDAO;
import com.dlyd.application.gbm.service.dao.ReportingStatus;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.noc.hccp.platform.common.PLOG;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

//@Component
public class ReportingStatusDAOImpl implements ReportingStatusDAO {

  private static final RowMapper<ReportingStatus> RM = new RowMapper<ReportingStatus>() {
    @Override
    public ReportingStatus mapRow(ResultSet resultSet, int i) throws SQLException {
      ReportingStatus s = new ReportingStatus();
      s.setDate(resultSet.getInt("report_date"));
      s.setStage(resultSet.getInt("stage"));
      s.setStatus(resultSet.getInt("status"));
      s.setCurrentBatchNo(resultSet.getInt("batch_no"));
      return s;
    }
  };

  private final JdbcTemplate jdbcTemplate;

  public ReportingStatusDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public void insert(ReportingStatus status) {
    try {
      jdbcTemplate.update("INSERT INTO gbm_status (report_date, stage, status, batch_no) VALUES(?,?,?,?)", preparedStatement -> {
        preparedStatement.setInt(1, status.getDate());
        preparedStatement.setInt(2, status.getStage());
        preparedStatement.setInt(3, status.getStatus());
        preparedStatement.setInt(4, status.getCurrentBatchNo());
      });
    } catch (Throwable e) {
      PLOG.normal.debug(e.getMessage(), e);
      throw new DataException(e);
    }
  }

  @Override
  public ReportingStatus select(int date) {
    PLOG.normal.debug("==========================date {}",date);
    try {
      return jdbcTemplate.queryForObject("SELECT * FROM gbm_status WHERE report_date = ?", RM, date);
    } catch (EmptyResultDataAccessException er) {
      PLOG.normal.debug("-----------------------------------------");
      return null;
    } catch (Throwable e) {
      PLOG.normal.debug(e.getMessage(), e);
      throw new DataException(e);
    }
  }

  @Override
  public void update(int date, int stage, int status) {
    if (0 == jdbcTemplate.update("UPDATE gbm_status SET stage = ?, status = ? WHERE report_date = ?", preparedStatement -> {
      preparedStatement.setInt(1, stage);
      preparedStatement.setInt(2, status);
      preparedStatement.setInt(3, date);
    })) {
      throw new DataNotExistException(String.format("date [%d] not exist", date));
    }
  }

  @Override
  public int iagBatchNo(int date) {
    if (0 == jdbcTemplate.update("UPDATE gbm_status SET batch_no = batch_no+1 WHERE report_date = ?", preparedStatement -> {
      preparedStatement.setInt(1, date);
    })) {
      throw new DataNotExistException(String.format("date [%d] not exist", date));
    }
    return jdbcTemplate.queryForObject("SELECT batch_no FROM gbm_status WHERE report_date = ?", Integer.class, date);
  }

  @Override
  public void delete(int date) {
    jdbcTemplate.update("DELETE  FROM gbm_status where report_date =?", preparedStatement -> {
      preparedStatement.setInt(1, date);
    });
  }
}
