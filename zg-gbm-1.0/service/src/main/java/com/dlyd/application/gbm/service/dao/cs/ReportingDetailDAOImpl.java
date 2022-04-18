package com.dlyd.application.gbm.service.dao.cs;

import com.dlyd.application.gbm.service.KtkjInfo;
import com.dlyd.application.gbm.service.common.AppConst;
import com.dlyd.application.gbm.service.dao.ReportingBatch;
import com.dlyd.application.gbm.service.dao.ReportingDetail;
import com.dlyd.application.gbm.service.dao.ReportingDetailDAO;
import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jdt.internal.compiler.classfmt.MethodInfoWithAnnotations;
import org.noc.hccp.platform.common.PLOG;
import org.noc.hccp.platform.common.PlatformException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletionException;

//@Component
public class ReportingDetailDAOImpl implements ReportingDetailDAO {

  private static final RowMapper<ReportingDetail> RM = new RowMapper() {
    @Override
    public Object mapRow(ResultSet resultSet, int i) throws SQLException {
      ReportingDetail d = new ReportingDetail();
      KtkjInfo info = new KtkjInfo();
      d.setInfo(info);
      d.setCtlDate(resultSet.getInt("ctl_date"));
      d.setCtlStage(resultSet.getInt("ctl_stage"));
      d.setCtlStatus(resultSet.getInt("ctl_status"));
      d.setCtlLastBatchNo(resultSet.getInt("ctl_last_bn"));
      info.setBankId(resultSet.getString("inf_bankId"));
      info.setBalance(resultSet.getString("inf_balance"));
      info.setCounterBankId(resultSet.getString("inf_counterBankId"));
      info.setCounterBankName(resultSet.getString("inf_counterBankName"));
      info.setCurrency(resultSet.getString("inf_currency"));
      info.setHandFee(resultSet.getString("inf_handFee"));
      info.setLoanSign(resultSet.getString("inf_loanSign"));
      info.setMark(resultSet.getString("inf_mark"));
      info.setRemarks(resultSet.getString("inf_remarks"));
      info.setTransAmount(resultSet.getString("inf_transAmount"));
      info.setTotal(resultSet.getString("inf_total"));
      info.setTransHour(resultSet.getString("inf_transHour"));
      info.setTransType(resultSet.getString("inf_transType"));
      info.setVoucherNumber(resultSet.getString("inf_voucherNumber"));
      info.setPzNumber(resultSet.getString("inf_pzNumber"));
      info.setPayeeName(resultSet.getString("inf_payeeName"));
      info.setUseFunds(resultSet.getString("inf_useFunds"));
      info.setVirtualBankId(resultSet.getString("inf_virtualBankId"));
      return d;
    }
  };

  private final JdbcTemplate jdbcTemplate;

  public ReportingDetailDAOImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<ReportingDetail> list(int date) {
    List<ReportingDetail> rl = jdbcTemplate.query("SELECT * FROM gbm_transaction WHERE ctl_date = ?", RM, date);
    if (rl != null) {
      return rl;
    }
    return Collections.emptyList();
  }

  @Override
  public void insert(List<KtkjInfo> infoList, int date, int stage, int status, int batchNo) {
    final int[] rowNum = {0};
    final int[] i1 = {0};
    final int batchSize = 100;
    List<List<KtkjInfo>> batchLists = Lists.partition(infoList, batchSize);
    String sql = "INSERT INTO gbm_transaction(ctl_date,ctl_stage,ctl_status,ctl_last_bn,inf_bankId,inf_balance,inf_counterBankId,inf_counterBankName,inf_currency,inf_handFee,inf_loanSign,inf_mark,inf_remarks,inf_transAmount,inf_total,inf_transHour,inf_transType,inf_voucherNumber,inf_pzNumber,inf_payeeName,inf_useFunds,inf_virtualBankId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    for (int index = 0; index < infoList.size(); index++) {
      try {
        KtkjInfo info = infoList.get(index);
        jdbcTemplate.update(sql, preparedStatement -> {
          preparedStatement.setInt(1, date);
          preparedStatement.setInt(2, stage);
          preparedStatement.setInt(3, status);
          preparedStatement.setInt(4, batchNo);
          preparedStatement.setString(5, info.getBankId());
          preparedStatement.setString(6, info.getBalance());
          preparedStatement.setString(7, info.getCounterBankId());
          preparedStatement.setString(8, info.getCounterBankName());
          preparedStatement.setString(9, info.getCurrency());
          preparedStatement.setString(10, info.getHandFee());
          preparedStatement.setString(11, info.getLoanSign());
          preparedStatement.setString(12, info.getMark());
          preparedStatement.setString(13, info.getRemarks());
          preparedStatement.setString(14, info.getTransAmount());
          preparedStatement.setString(15, info.getTotal());
          preparedStatement.setString(16, info.getTransHour());
          preparedStatement.setString(17, info.getTransType());
          preparedStatement.setString(18, info.getVoucherNumber());
          preparedStatement.setString(19, info.getPzNumber());
          preparedStatement.setString(20, info.getPayeeName());
          preparedStatement.setString(21, info.getUseFunds());
          preparedStatement.setString(22, info.getVirtualBankId());
        });
      } catch (DataAccessException e) {
        String format = String.format(AppConst.FileNameForamt.TRANSACTIONS, date);
        String msg = e.getMessage();
        int lastIndexOf = StringUtils.lastIndexOf(msg, "ORA-");
        String substring = StringUtils.substring(msg, lastIndexOf);
        int j = index + 1;
        throw new PlatformException(format + "明细文件，第" + j + "行错误，" + substring, e.getCause());
      }
    }


  }

  @Override
  public int update(int date, int batchNo, String bankId, int stage, int status) {
    return jdbcTemplate.update("UPDATE gbm_transaction SET ctl_stage = ?, ctl_status = ? WHERE ctl_date = ? AND ctl_last_bn = ? AND inf_bankId = ?", preparedStatement -> {
      preparedStatement.setInt(1, stage);
      preparedStatement.setInt(2, status);
      preparedStatement.setInt(3, date);
      preparedStatement.setInt(4, batchNo);
      preparedStatement.setString(5, bankId);
    });
  }

  @Override
  public int updateCodeAndPrompt(String voucherNumber, String code, String prompt) {
    return jdbcTemplate.update("UPDATE gbm_transaction SET inf_code = ?, inf_prompt = ? WHERE inf_voucherNumber = ?", preparedStatement -> {
      preparedStatement.setString(1, code);
      preparedStatement.setString(2, prompt);
      preparedStatement.setString(3, voucherNumber);
    });
  }

  @Override
  public List<ReportingDetail> selectByCtlDate(int date) {
    String sql = "select * from gbm_transaction where ctl_date = ? ";
    return jdbcTemplate.query(sql, RM, date);
  }

  @Override
  public List<ReportingDetail> listByVoucherNumbers(List<String> voucherNumbers) {
    String sqlInParam = CharMatcher.is(',').trimFrom(Strings.repeat("?,", voucherNumbers.size()));
    List<ReportingDetail> rl = jdbcTemplate.query("SELECT * FROM gbm_transaction WHERE inf_voucherNumber in (" + sqlInParam + ")", RM, voucherNumbers.toArray());
    if (rl != null) {
      return rl;
    }
    return Collections.emptyList();
  }

  @Override
  public void delete(int date) {
    jdbcTemplate.update("DELETE  FROM gbm_transaction where ctl_date = ? ", preparedStatement -> {
      preparedStatement.setInt(1, date);
    });
  }

  @Override
  public void updateSuccess(ReportingBatch batch) {
    jdbcTemplate.update("update gbm_transaction set inf_code='jy_200' where ctl_date = ? ", preparedStatement -> {
      preparedStatement.setInt(1, batch.getDate());
    });
  }

  public static void main(String[] args) {
    List<String> stringList = new ArrayList<>();
    stringList.add("1");
    stringList.add("2");
    String s = ArrayUtils.toString(stringList);
    System.out.println(s);


  }

}
