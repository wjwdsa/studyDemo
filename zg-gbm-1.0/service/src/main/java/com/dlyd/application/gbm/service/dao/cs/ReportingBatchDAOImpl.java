package com.dlyd.application.gbm.service.dao.cs;

import com.dlyd.application.gbm.service.DataException;
import com.dlyd.application.gbm.service.DataNotExistException;
import com.dlyd.application.gbm.service.dao.ReportingBatch;
import com.dlyd.application.gbm.service.dao.ReportingBatchDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import org.noc.hccp.platform.common.PLOG;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

//@Component
public class ReportingBatchDAOImpl implements ReportingBatchDAO {

  private static final RowMapper<ReportingBatch> RM = new RowMapper<ReportingBatch>() {
    @Override
    public ReportingBatch mapRow(ResultSet resultSet, int i) throws SQLException {
      ReportingBatch b = new ReportingBatch();
      b.setDate(resultSet.getInt("report_date"));
      b.setNo(resultSet.getInt("no"));
      b.setStatus(resultSet.getInt("status"));
      b.setBeginTime(resultSet.getTimestamp("begin_time"));
      b.setEndTime(resultSet.getTimestamp("end_time"));
      return b;
    }
  };

  private final JdbcTemplate jdbcTemplate;

  public ReportingBatchDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public ReportingBatch select(int date, int no) {
    try {
      return jdbcTemplate.queryForObject("SELECT * FROM gbm_batch WHERE report_date = ? AND no = ?", RM, date, no);
    } catch (EmptyResultDataAccessException er) {
      throw new DataNotExistException(String.format("batch (date[%d] no[%d]) not exist", date, no));
    } catch (Throwable e) {
      PLOG.normal.debug(e.getMessage(), e);
      throw new DataException(e);
    }
  }

  @Override
  public void insert(ReportingBatch batch) {
    jdbcTemplate.update("INSERT INTO gbm_batch(report_date, no, status, begin_date) VALUES(?,?,?,?)", preparedStatement -> {
      preparedStatement.setInt(1, batch.getDate());
      preparedStatement.setInt(2, batch.getNo());
      preparedStatement.setInt(3, batch.getStatus());
      preparedStatement.setTimestamp(4, Timestamp.from(Instant.now()));
    });
  }

  @Override
  public void end(int date, int no, int status) {
    if (0 == jdbcTemplate.update("UPDATE gbm_batch SET status = ?, end_date = ? WHERE report_date = ? AND no = ?", preparedStatement -> {
      preparedStatement.setInt(1, status);
      preparedStatement.setTimestamp(2, Timestamp.from(Instant.now()));
      preparedStatement.setInt(3, date);
      preparedStatement.setInt(4, no);
    })) {
      throw new DataNotExistException(String.format("batch (date[%d] no[%d]) not exist", date, no));
    }
  }

  @Override
  public void delete(int date) {
    jdbcTemplate.update("DELETE  FROM gbm_batch where report_date =?", preparedStatement -> {
      preparedStatement.setInt(1, date);
    });
  }

}
