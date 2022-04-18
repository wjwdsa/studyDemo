package com.dlyd.application.gbm.service.common;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.noc.hccp.platform.common.AppLog;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public interface AppConst {

  /**
   * 虚拟操作员
   */
  String VIRTUAL_OPERATOR = "SJJRPT01";
  /**
   * 虚拟网点
   */
  String VIRTUAL_BRANCH = "001";

  String SPLIT_PIPE = "\\|";
  String PIPE = "|";

  String PSS_SE_NO_FORMAT = "%1$s%2$s%3$4s%4$09d";
  String PSS = "PSS";
  String YHBH = "819";
  String B_SYSTEM_ID = "5199000101";
  String APP_ID = "SI";
  String XZQHDM="510300";

  final class DTPattern {
    public static final String DATE_PATTERN = "yyyyMMdd";
    public static final String TIME_PATTERN = "HHmmssSSS";
    public static final String yyMMdd_PATTERN = "yyMMdd";
  }

  final class SiServiceId {
    public static final String GET_JYLSH = "jylsh";
    public static final String CALL_BUSINESS = "callBusiness";
    public static final String SZ_CALL = "szCall";
  }

  final class FileNameForamt {
    public static final String RORS_0230001 = "0230001";
    public static final String RORS_0211001 = "0211001";
    public static final String RORS_0211002 = "0211002";

    /**
     * 社保基金账号余额查询文件命名格式
     * 银行编号ToRs_社保经办机构_日期（yyyyMMdd）_行政区划.txt
     */
    public static final String TO_RS_FORMAT_0230001 = "%sToRs_0230001_%s_%s.txt";

    /**
     * 社保基金账号收支明细文件命名格式
     * 银行编号ToRs_文件类型代码(0211001账户收支明细)_日期（yyyyMMdd）_行政区划.txt
     */
    public static final String TO_RS_FORMAT_0211001 = "%sToRs_0211001_%s_%s.txt";

    /**
     * 对账文件命名格式
     * 银行编号ToRs_文件类型代码(0251001对账文件)_日期（yyyyMMdd）.txt
     */
    public static final String TO_RS_FORMAT_0251001 = "%sToRs_0251001_%s.txt";

    /**
     * 社保基金账号收支明细电子回单命名格式
     * <p>
     * 银行编号ToRs_文件类型代码（0211002账户收支明细电子回单）_日期（yyyyMMdd）_收支明细流水号_行政区划.txt
     */
    public static final String TO_RS_FORMAT_0211002 = "%sToRs_0211002_%s_%s_%s.pdf";

    /**
     * 对账文件命名格式
     * SI_CBS_RECONCILIATION_日期(yyyyMMdd).txt
     */
    public static final String RECONCILIATION = "SI_CBS_RECONCILIATION_%s.txt";

    /**
     * 交易明细文件格式
     * SI_CBS_ACCOUNTFILES_日期(yyyyMMdd).txt
     */
    public static final String ACCOUNTFILES = "SI_CBS_ACCOUNTFILES_%s.txt";

    /**
     * 代发文件
     * BATCH_TRANS_PS_代发批次号.txt
     */
    public static final String BATCH_TRANS = "BATCH_TRANS_PS_%s.txt";

    /**
     * 代发结果文件
     * BATCH_RESULT_PS_代发批次号.txt
     */
    public static final String BATCH_RESULT = "BATCH_RESULT_PS_%s.txt";

    /**
     * 代发回盘文件格式
     * 统筹区编码_报盘期号_险种_银行行别_金额_发放对象_支付类型.txt
     */
    public static final String HPWJ = "%s_%s_%s_%s_%s_%s_%s.txt";

    public static final String TRANSACTIONS = "GBM_CBS_TRANSACTIONS_%s.txt";

  }

  /**
   * 核对账目动作
   */
  final class VOA {
    /**
     * 核心对账通知
     */
    public static final String DZTZ = "-1";
    /**
     * 核心对账
     */
    public static final String DZ = "00";
    /**
     * 对账信息上传
     */
    public static final String DZSC = "01";
    /**
     * 对账信息确认
     */
    public static final String DZQR = "02";
    /**
     * 对账结果信息获取
     */
    public static final String DZJGHQ = "03";
    /**
     * 对账失败信息确认
     */
    public static final String DZSBQR = "04";
  }

  final class Status {
    public static final String SUCCESS = "S";
    public static final String FAIL = "F";
    public static final String UNKNOWN = "U";
  }

  final class SI {
    public static final String SUCCESS = "1";
    public static final String FAIL = "-1";
    public static final String ALTER = "100";
  }


  final class ConStatus {
    /**
     * 链接失败
     */
    public static final String Status_0 = "0";
    /**
     * 链接成功，信息发送失败
     */
    public static final String Status_1 = "1";
    /**
     * 信息已发送，未响应
     */
    public static final String Status_2 = "2";
    /**
     * 信息已发送，已响应
     */
    public static final String Status_3 = "3";
  }

  final class ResStatus {
    /**
     * 成功
     */
    public static final String SUCCESS = "S";
    /**
     * 失败
     */
    public static final String FAIL = "F";
  }

  abstract class Address {
    public static String AddressIP = null;

    static {
      try {
        AddressIP = InetAddress.getLocalHost().getHostAddress();
        AppLog.logger.info("本机IP：" + AddressIP);
      } catch (UnknownHostException e) {
        AppLog.logger.error("获取本机IP错误", e);
      }
    }
  }

  abstract class SeqNo {
    public static String AddressIP = null;

    static {
      try {
        AddressIP = InetAddress.getLocalHost().getHostAddress();
        AppLog.logger.info("本机IP：" + AddressIP);
      } catch (UnknownHostException e) {
        AppLog.logger.error("获取本机IP错误", e);
      }
    }

    public static String createEsbSeqNo(String seqNo) {
//      String sh_SeqNo = String.format("%1$s%2$s%3$03d", "BCP",
//              DateFormatUtils.format(new Date(), "yyyyMMddHHmmss"), Integer.parseInt(ptlsh.substring(ptlsh.length()-3)));
      return String.format(PSS_SE_NO_FORMAT, PSS, VIRTUAL_BRANCH,
              DateFormatUtils.format(new Date(), DTPattern.yyMMdd_PATTERN), Integer.parseInt(seqNo));
    }
  }

}

