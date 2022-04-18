package com.dlyd.application.gbm.service.dao.cs;

import com.dlyd.application.gbm.service.DataNotExistException;
import com.dlyd.application.gbm.service.dao.ReportingAccountDAO;
import com.dlyd.application.gbm.service.dao.ReportingAccount;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

//@Component
public class ReportingAccountDAOImpl implements ReportingAccountDAO {

  private static final RowMapper<ReportingAccount> RM = new RowMapper<ReportingAccount>() {
    @Override
    public ReportingAccount mapRow(ResultSet resultSet, int i) throws SQLException {
      ReportingAccount a = new ReportingAccount();
      a.setAccountNumber(resultSet.getString("acc_no"));
      a.setAccountType(resultSet.getString("acc_type"));
      a.setDocumentReferenceNo(resultSet.getString("doc_ref_no"));
      a.setStatus(resultSet.getInt("status"));
      a.setAddDate(resultSet.getInt("add_date"));
      a.setDeleteDate(resultSet.getInt("del_date"));

      return a;
    }
  };

  private final JdbcTemplate jdbcTemplate;

  public ReportingAccountDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<ReportingAccount> listNormal() {
    List<ReportingAccount> rl = jdbcTemplate.query("SELECT * from gbm_account WHERE status = 1 ORDER BY acc_no", RM);
    if (rl == null) {
      return Collections.emptyList();
    }
    return rl;
  }

  @Override
  public void insert(ReportingAccount a) {
    String sql = "INSERT INTO gbm_account VALUES (?,?,?,?,?)";
    jdbcTemplate.update(sql, preparedStatement -> {
      preparedStatement.setString(1, a.getAccountNumber());
      preparedStatement.setString(2, a.getDocumentReferenceNo());
      preparedStatement.setInt(3, a.getStatus());
      preparedStatement.setInt(4, a.getAddDate());
      preparedStatement.setInt(5, a.getDeleteDate());
    });
  }

  @Override
  public void update(ReportingAccount a) {
    String sql = "UPDATE gbm_account SET doc_ref_no = ? WHERE acc_no = ?";
    if (0 == jdbcTemplate.update(sql, preparedStatement -> {
      preparedStatement.setString(1, a.getDocumentReferenceNo());
      preparedStatement.setString(2, a.getAccountNumber());
    })) {
      throw new DataNotExistException(a.getAccountNumber());
    }
  }

  @Override
  public void delete(String accountNumber, int date) {
    String sql = "UPDATE gbm_account SET status = ?, del_date = ? WHERE acc_no = ?";
    if (0 == jdbcTemplate.update(sql, preparedStatement -> {
      preparedStatement.setInt(1, ReportingAccount.STATUS_DELETE);
      preparedStatement.setInt(2, date);
      preparedStatement.setString(3, accountNumber);
    })) {
      throw new DataNotExistException(accountNumber);
    }
  }

  @Override
  public List<ReportingAccount> selectAll() {
    String sql = "select * from gbm_account";
    return jdbcTemplate.query(sql, RM);
  }

}
