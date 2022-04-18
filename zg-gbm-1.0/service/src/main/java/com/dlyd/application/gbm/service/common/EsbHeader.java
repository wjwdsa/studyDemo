package com.dlyd.application.gbm.service.common;

import com.dlyd.application.lib.esb.msg11.ESBMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

public class EsbHeader {

  public static ESBMessage.Builder createRequestBuilerHeader(ESBMessage.Visitor visitor, String messageType, String messageCode) {
    ESBMessage.Builder requestBuilder = ESBMessage.newBuilder();
    Date now = new Date();
    // sys-header
    requestBuilder.setSH_ServiceCode("SVR_INQUIRY");
    // 消息类型
    requestBuilder.setSH_MessageType(messageType);
    // 消息编码
    requestBuilder.setSH_MessageCode(messageCode);
    // 交易码
    requestBuilder.setSH_TranCode(StringUtils.EMPTY);
    // 交易模式
    requestBuilder.setSH_TranMode(visitor.getSH_TranMode());
    // 渠道类型
    requestBuilder.setSH_SourceType(visitor.getSH_SourceType());
    // 发送方机构ID
    requestBuilder.setSH_BranchId(visitor.getSH_BranchId());
    // 请求系统编号
    requestBuilder.setSH_ConsumerId(visitor.getSH_ConsumerId());
    // 服务请求者身份
    requestBuilder.setSH_UserId(visitor.getSH_UserId());
    // 交易日期
    requestBuilder.setSH_TranDate(visitor.getSH_TranDate());
    // 交易时间
    requestBuilder.setSH_TranTimestamp(visitor.getSH_TranTimestamp());
    // 服务器标识
    requestBuilder.setSH_ServerId(visitor.getSH_ServerId());
    // 终端标识
    requestBuilder.setSH_WsId("05");
    // 流水号
    requestBuilder.setSH_SeqNo(visitor.getSH_SeqNo());
    // 源节点编号
    requestBuilder.setSH_SourceBranchNo(visitor.getSH_SourceBranchNo());
    // 目标节点编号
    requestBuilder.setSH_DestBranchNo(visitor.getSH_DestBranchNo());
    // 模块标识
    requestBuilder.setSH_ModuleId("RB");
    // 用户语言
    requestBuilder.setSH_UserLang(visitor.getSH_UserLang());
    // 核心流水号
    requestBuilder.setSH_CoreSeqNo(visitor.getSH_CoreSeqNo());
    // 柜面流水号
    requestBuilder.setSH_TellerSeqNo(visitor.getSH_TellerSeqNo());
    // 复核标志
    requestBuilder.setSH_ApprFlag(visitor.getSH_ApprFlag());
    // 交易屏幕标识
    requestBuilder.setSH_ProgramId(visitor.getSH_ProgramId());
    // 授权标志
    requestBuilder.setSH_AuthFlag(visitor.getSH_AuthFlag());
    //交易类型
    requestBuilder.setSH_TranType(visitor.getSH_TranType());
    // 交易录入柜员标识
    requestBuilder.setSH_ApprUserId(visitor.getSH_ApprUserId());
    // 授权柜员标识
    requestBuilder.setSH_AuthFlag(visitor.getSH_AuthFlag());
    // 授权柜员密码
    requestBuilder.setSH_AuthPassword(visitor.getSH_AuthPassword());
    // 冲正交易类型
    requestBuilder.setSH_ReversalTranType(visitor.getSH_ReversalTranType());
    // 文件绝对路径
    requestBuilder.setSH_FilePath(visitor.getSH_FilePath());
    //app-header
    requestBuilder.setAH_PgupOrPgdn("0");
    requestBuilder.setAH_TotalNum("-1");
    requestBuilder.setAH_CurrentNum(visitor.getAH_CurrentNum());
    requestBuilder.setAH_PageStart(visitor.getAH_PageStart());
    requestBuilder.setAH_PageEnd(visitor.getAH_PageEnd());
    requestBuilder.setAH_TotalRows(visitor.getAH_TotalRows());
    requestBuilder.setAH_TotalFlag(visitor.getAH_TotalFlag());
    //local-header
    requestBuilder.addLH_RetNullable();
    return requestBuilder;
  }


  public static ESBMessage.Builder createRequestBuilerHeader(String primaryTraceNo, String messageType, String messageCode) {
    ESBMessage.Builder requestBuilder = ESBMessage.newBuilder();
    Date now = new Date();
    // sys-header
    requestBuilder.setSH_ServiceCode("SVR_INQUIRY");
    // 消息类型
    requestBuilder.setSH_MessageType(messageType);
    // 消息编码
    requestBuilder.setSH_MessageCode(messageCode);
    // 交易码
    requestBuilder.setSH_TranCode(StringUtils.EMPTY);
    // 交易模式
    requestBuilder.setSH_TranMode("ONLINE");
    // 渠道类型
    requestBuilder.setSH_SourceType("PS");
    // 发送方机构ID
    requestBuilder.setSH_BranchId(AppConst.VIRTUAL_BRANCH);
    // 请求系统编号
    requestBuilder.setSH_ConsumerId("PSS");
    // 服务请求者身份
    requestBuilder.setSH_UserId(AppConst.VIRTUAL_OPERATOR);
    // 交易日期
    requestBuilder.setSH_TranDate(DateFormatUtils.format(now, AppConst.DTPattern.DATE_PATTERN));
    // 交易时间
    requestBuilder.setSH_TranTimestamp(DateFormatUtils.format(now, AppConst.DTPattern.TIME_PATTERN));
    // 服务器标识
    requestBuilder.setSH_ServerId(AppConst.Address.AddressIP);
    // 终端标识
    requestBuilder.setSH_WsId("01");
    // 流水号
    requestBuilder.setSH_SeqNo(AppConst.SeqNo.createEsbSeqNo(primaryTraceNo));
    // 源节点编号
    requestBuilder.setSH_SourceBranchNo("286ED488C7430");
    // 目标节点编号
    requestBuilder.setSH_DestBranchNo(StringUtils.EMPTY);
    // 模块标识
    requestBuilder.setSH_ModuleId("RB");
    // 用户语言
    requestBuilder.setSH_UserLang("CHINESE");
    // 核心流水号
    requestBuilder.setSH_CoreSeqNo(StringUtils.EMPTY);
    // 柜面流水号
    requestBuilder.setSH_TellerSeqNo(StringUtils.EMPTY);
    // 复核标志
    requestBuilder.setSH_ApprFlag(StringUtils.EMPTY);
    // 交易屏幕标识
    requestBuilder.setSH_ProgramId(StringUtils.EMPTY);
    // 交易类型
    requestBuilder.setSH_AuthPassword(StringUtils.EMPTY);
    // 交易录入柜员标识
    requestBuilder.setSH_ApprUserId(StringUtils.EMPTY);
    // 授权柜员标识
    requestBuilder.setSH_AuthFlag("N");
    // 授权柜员密码
    requestBuilder.setSH_AuthPassword(StringUtils.EMPTY);
    // 冲正交易类型
    requestBuilder.setSH_ReversalTranType(StringUtils.EMPTY);
    // 文件绝对路径
    requestBuilder.setSH_FilePath(StringUtils.EMPTY);
    //app-header
    requestBuilder.setAH_PgupOrPgdn("0");
    requestBuilder.setAH_TotalNum("-1");
    requestBuilder.setAH_CurrentNum(StringUtils.EMPTY);
    requestBuilder.setAH_PageStart(StringUtils.EMPTY);
    requestBuilder.setAH_PageEnd(StringUtils.EMPTY);
    requestBuilder.setAH_TotalRows(StringUtils.EMPTY);
    requestBuilder.setAH_TotalFlag(StringUtils.EMPTY);
    requestBuilder.setAH_TotalFlag(StringUtils.EMPTY);
    //local-header
    requestBuilder.addLH_RetNullable();
    return requestBuilder;

  }

  public static ESBMessage.Builder createRequestBuilerHeaderCZ(ESBMessage.Visitor visitor, String primaryTraceNo, String messageType, String messageCode) {
    ESBMessage.Builder requestBuilder = ESBMessage.newBuilder();
    Date now = new Date();
    // sys-header
    requestBuilder.setSH_ServiceCode("SVR_REVERSAL");
    // 消息类型
    requestBuilder.setSH_MessageType(messageType);
    // 消息编码
    requestBuilder.setSH_MessageCode(messageCode);
    // 交易码
    requestBuilder.setSH_TranCode(StringUtils.EMPTY);
    // 交易模式
    requestBuilder.setSH_TranMode("ONLINE");
    // 渠道类型
    requestBuilder.setSH_SourceType("MT");
    // 发送方机构ID
    requestBuilder.setSH_BranchId(visitor.getSH_BranchId());//取柜面传的值
    // 请求系统编号
    requestBuilder.setSH_ConsumerId("PSS");
    // 服务请求者身份
    requestBuilder.setSH_UserId(visitor.getSH_UserId());//取柜面传的值
    // 交易日期
    requestBuilder.setSH_TranDate(visitor.getSH_TranDate());//取柜面传的值
    // 交易时间
    requestBuilder.setSH_TranTimestamp(DateFormatUtils.format(now, AppConst.DTPattern.TIME_PATTERN));
    // 服务器标识
    requestBuilder.setSH_ServerId(AppConst.Address.AddressIP);
    // 终端标识
    requestBuilder.setSH_WsId("01");
    // 流水号
    requestBuilder.setSH_SeqNo(AppConst.SeqNo.createEsbSeqNo(primaryTraceNo));
    // 源节点编号
    requestBuilder.setSH_SourceBranchNo("286ED488C7430");
    // 目标节点编号
    requestBuilder.setSH_DestBranchNo(StringUtils.EMPTY);
    // 模块标识
    requestBuilder.setSH_ModuleId("RB");
    // 用户语言
    requestBuilder.setSH_UserLang("CHINESE");
    // 核心流水号
    requestBuilder.setSH_CoreSeqNo(StringUtils.EMPTY);
    // 柜面流水号
    requestBuilder.setSH_TellerSeqNo(StringUtils.EMPTY);
    // 复核标志
    requestBuilder.setSH_ApprFlag(StringUtils.EMPTY);
    // 交易屏幕标识
    requestBuilder.setSH_ProgramId(StringUtils.EMPTY);
    // 交易类型
    requestBuilder.setSH_AuthPassword(StringUtils.EMPTY);
    // 交易录入柜员标识
    requestBuilder.setSH_ApprUserId(StringUtils.EMPTY);
    //交易类型
    requestBuilder.setSH_TranType(StringUtils.EMPTY);
    // 授权柜员标识
    requestBuilder.setSH_AuthFlag("M");
    // 授权柜员密码
    requestBuilder.setSH_AuthPassword(StringUtils.EMPTY);
    // 冲正交易类型
    requestBuilder.setSH_ReversalTranType(StringUtils.EMPTY);
    // 文件绝对路径
    requestBuilder.setSH_FilePath(StringUtils.EMPTY);
    //app-header
    requestBuilder.setAH_PgupOrPgdn("0");
    requestBuilder.setAH_TotalNum("-1");
    requestBuilder.setAH_CurrentNum(StringUtils.EMPTY);
    requestBuilder.setAH_PageStart(StringUtils.EMPTY);
    requestBuilder.setAH_PageEnd(StringUtils.EMPTY);
    requestBuilder.setAH_TotalRows(StringUtils.EMPTY);
    requestBuilder.setAH_TotalFlag(StringUtils.EMPTY);
    requestBuilder.setAH_TotalFlag(StringUtils.EMPTY);
    //local-header
    requestBuilder.addLH_RetNullable();
    return requestBuilder;

  }

  public static ESBMessage.Builder createDefautSuccessResponseBuilder(ESBMessage esbMessage) {
    return createResponseBuilder(esbMessage, AppConst.Status.SUCCESS, "000000", StringUtils.EMPTY);
  }


  public static ESBMessage.Builder createDefautFailResponseBuilder(ESBMessage esbMessage, String code, String message) {
    return createResponseBuilder(esbMessage, AppConst.Status.FAIL, code, message);
  }

  public static ESBMessage.Builder createResponseBuilder(ESBMessage esbMessage, String retStatus, String retCode, String retMsg) {
    ESBMessage.Builder builder = esbMessage.newResponseBuilder();
    ESBMessage.Visitor visitor = esbMessage.getVisitor();
    builder.setSH_RetStatus(retStatus);
    builder.addSH_Ret(retCode, retMsg);
    builder.setSH_MessageType(visitor.getSH_MessageType());
    builder.setSH_MessageCode(visitor.getSH_MessageCode());
    builder.setSH_TranDate(DateFormatUtils.format(new Date(), AppConst.DTPattern.DATE_PATTERN));
    builder.setSH_TranTimestamp(DateFormatUtils.format(new Date(), AppConst.DTPattern.TIME_PATTERN));
    builder.setSH_SeqNo(visitor.getSH_SeqNo());
    builder.setSH_MacValue(StringUtils.EMPTY);
    //app-header
    builder.setAH_PgupOrPgdn("0");
    builder.setAH_TotalNum(StringUtils.EMPTY);
    builder.setAH_CurrentNum(StringUtils.EMPTY);
    builder.setAH_PageStart(StringUtils.EMPTY);
    builder.setAH_PageEnd(StringUtils.EMPTY);
    builder.setAH_TotalRows(StringUtils.EMPTY);
    builder.setAH_TotalPages(StringUtils.EMPTY);
    builder.setAH_TotalFlag(StringUtils.EMPTY);
    //local-header
    builder.addLH_Ret(StringUtils.EMPTY, StringUtils.EMPTY);

    return builder;
  }

}
