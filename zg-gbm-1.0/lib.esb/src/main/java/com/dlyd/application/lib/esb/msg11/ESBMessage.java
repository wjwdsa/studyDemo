package com.dlyd.application.lib.esb.msg11;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.PlatformException;
import org.noc.hccp.platform.common.msg.Message;

public class ESBMessage extends Message<ESBMessage> {

  private static final String SYS_HEAD = "SYS_HEAD";
  private static final String APP_HEAD = "APP_HEAD";
  private static final String LOCAL_HEAD = "LOCAL_HEAD";

  private static final String SERVICE_CODE = "SERVICE_CODE";
  private static final String MESSAGE_TYPE = "MESSAGE_TYPE";
  private static final String MESSAGE_CODE = "MESSAGE_CODE";
  private static final String TRAN_CODE = "TRAN_CODE";
  private static final String TRAN_MODE = "TRAN_MODE";
  private static final String SOURCE_TYPE = "SOURCE_TYPE";
  private static final String BRANCH_ID = "BRANCH_ID";
  private static final String CONSUMER_ID = "CONSUMER_ID";
  private static final String USER_ID = "USER_ID";
  private static final String TRAN_DATE = "TRAN_DATE";
  private static final String TRAN_TIMESTAMP = "TRAN_TIMESTAMP";
  private static final String SERVER_ID = "SERVER_ID";
  private static final String WS_ID = "WS_ID";
  private static final String SEQ_NO = "SEQ_NO";
  private static final String SOURCE_BRANCH_NO = "SOURCE_BRANCH_NO";
  private static final String DEST_BRANCH_NO = "DEST_BRANCH_NO";
  private static final String MODULE_ID = "MODULE_ID";
  private static final String USER_LANG = "USER_LANG";
  private static final String CORE_SEQ_NO = "CORE_SEQ_NO";
  private static final String TELLER_SEQ_NO = "TELLER_SEQ_NO";
  private static final String APPR_FLAG = "APPR_FLAG";
  private static final String PROGRAM_ID = "PROGRAM_ID";
  private static final String AUTH_FLAG = "AUTH_FLAG";
  private static final String TRAN_TYPE = "TRAN_TYPE";
  private static final String APPR_USER_ID = "APPR_USER_ID";
  private static final String AUTH_USER_ID = "AUTH_USER_ID";
  private static final String AUTH_PASSWORD = "AUTH_PASSWORD";
  private static final String REVERSAL_TRAN_TYPE = "REVERSAL_TRAN_TYPE";
  private static final String FILE_PATH = "FILE_PATH";
  private static final String RET_STATUS = "RET_STATUS";

  private static final String RET = "RET";
  private static final String RET_CODE = "RET_CODE";
  private static final String RET_MSG = "RET_MSG";
  private static final String MAC_VALUE = "MAC_VALUE";

  private static final String PGUP_OR_PGDN = "PGUP_OR_PGDN";
  private static final String TOTAL_NUM = "TOTAL_NUM";
  private static final String CURRENT_NUM = "CURRENT_NUM";
  private static final String PAGE_START = "PAGE_START";
  private static final String PAGE_END = "PAGE_END";
  private static final String TOTAL_ROWS = "TOTAL_ROWS";
  private static final String TOTAL_PAGES = "TOTAL_PAGES";
  private static final String TOTAL_FLAG = "TOTAL_FLAG";

  private static final Map<String, Integer> _data_length_table = new HashMap<>(64);
  public static Object Visitor;

  static {
    _data_length_table.put(SERVICE_CODE, 24);
    _data_length_table.put(MESSAGE_TYPE, 4);
    _data_length_table.put(MESSAGE_CODE, 6);
    _data_length_table.put(TRAN_CODE, 8);//
    _data_length_table.put(TRAN_MODE, 10);
    _data_length_table.put(SOURCE_TYPE, 2);//
    _data_length_table.put(BRANCH_ID, 6);
    _data_length_table.put(CONSUMER_ID, 3);
    _data_length_table.put(USER_ID, 30);
    _data_length_table.put(TRAN_DATE, 8);
    _data_length_table.put(TRAN_TIMESTAMP, 9);
    _data_length_table.put(SERVER_ID, 30);
    _data_length_table.put(WS_ID, 30);
    _data_length_table.put(SEQ_NO, 22);
    _data_length_table.put(SOURCE_BRANCH_NO, 40);
    _data_length_table.put(DEST_BRANCH_NO, 40);
    _data_length_table.put(MODULE_ID, 2);
    _data_length_table.put(USER_LANG, 20);
    _data_length_table.put(CORE_SEQ_NO, 22);//
    _data_length_table.put(TELLER_SEQ_NO, 25);
    _data_length_table.put(APPR_FLAG, 1);
    _data_length_table.put(PROGRAM_ID, 8);
    _data_length_table.put(AUTH_FLAG, 1);
    _data_length_table.put(TRAN_TYPE, 4);
    _data_length_table.put(APPR_USER_ID, 30);
    _data_length_table.put(AUTH_USER_ID, 30);
    _data_length_table.put(AUTH_PASSWORD, 30);
    _data_length_table.put(REVERSAL_TRAN_TYPE, 4);
    _data_length_table.put(FILE_PATH, 512);
    _data_length_table.put(RET_STATUS, 1);
    _data_length_table.put(BRANCH_ID, 6);

    _data_length_table.put(RET_CODE, 6);
    _data_length_table.put(RET_MSG, 512);

    _data_length_table.put(MAC_VALUE, 50);
    _data_length_table.put(PGUP_OR_PGDN, 1);
    _data_length_table.put(TOTAL_NUM, 12);
    _data_length_table.put(CURRENT_NUM, 12);
    _data_length_table.put(PAGE_START, 12);
    _data_length_table.put(PAGE_END, 12);
    _data_length_table.put(TOTAL_ROWS, 12);
    _data_length_table.put(TOTAL_PAGES, 12);
    _data_length_table.put(TOTAL_FLAG, 1);

  }

  private static int getDataLength(String code) {
    if (_data_length_table.containsKey(code)) {
      return _data_length_table.get(code);
    }
    return 0;
  }


  public interface Visitor {

    String getSH_ServiceCode();

    String getSH_MessageType();

    String getSH_MessageCode();

    String getSH_TranCode();

    String getSH_TranMode();

    String getSH_SourceType();

    String getSH_BranchId();

    String getSH_ConsumerId();

    String getSH_UserId();

    String getSH_TranDate();

    String getSH_TranTimestamp();

    String getSH_ServerId();

    String getSH_WsId();

    String getSH_SeqNo();

    String getSH_SourceBranchNo();

    String getSH_DestBranchNo();

    String getSH_ModuleId();

    String getSH_UserLang();

    String getSH_CoreSeqNo();

    String getSH_TellerSeqNo();

    String getSH_ApprFlag();

    String getSH_ProgramId();

    String getSH_AuthFlag();

    String getSH_TranType();

    String getSH_ApprUserId();

    String getSH_AuthUserId();

    String getSH_AuthPassword();

    String getSH_ReversalTranType();

    String getSH_FilePath();

    String getSH_RetStatus();

    Array getSH_Ret();

    String getSH_RetCode();

    String getSH_RetMsg();

    String getSH_MacValue();

    String getAH_PgupOrPgdn();

    String getAH_TotalNum();

    String getAH_CurrentNum();

    String getAH_PageStart();

    String getAH_PageEnd();

    String getAH_TotalRows();

    String getAH_TotalPages();

    String getAH_TotalFlag();

    Array getLH_Ret();

    String getLH_RetCode();

    String getLH_RetMsg();

    Field getField(String name);

    Array getArray(String name);
  }

  private class VisitorImpl implements Visitor {

    private ESBMessage visiting;

    private VisitorImpl(ESBMessage visiting) {
      this.visiting = visiting;
    }

    @Override
    public String getSH_ServiceCode() {
      return getSysHeaderData(SERVICE_CODE);
    }

    @Override
    public String getSH_MessageType() {
      return getSysHeaderData(MESSAGE_TYPE);
    }

    @Override
    public String getSH_MessageCode() {
      return getSysHeaderData(MESSAGE_CODE);
    }

    @Override
    public String getSH_TranCode() {
      return getSysHeaderData(TRAN_CODE);
    }

    @Override
    public String getSH_TranMode() {
      return getSysHeaderData(TRAN_MODE);
    }

    @Override
    public String getSH_SourceType() {
      return getSysHeaderData(SOURCE_TYPE);
    }

    @Override
    public String getSH_BranchId() {
      return getSysHeaderData(BRANCH_ID);
    }

    @Override
    public String getSH_ConsumerId() {
      return getSysHeaderData(CONSUMER_ID);
    }

    @Override
    public String getSH_UserId() {
      return getSysHeaderData(USER_ID);
    }

    @Override
    public String getSH_TranDate() {
      return getSysHeaderData(TRAN_DATE);
    }

    @Override
    public String getSH_TranTimestamp() {
      return getSysHeaderData(TRAN_TIMESTAMP);
    }

    @Override
    public String getSH_ServerId() {
      return getSysHeaderData(SERVER_ID);
    }

    @Override
    public String getSH_WsId() {
      return getSysHeaderData(WS_ID);
    }

    @Override
    public String getSH_SeqNo() {
      return getSysHeaderData(SEQ_NO);
    }

    @Override
    public String getSH_SourceBranchNo() {
      return getSysHeaderData(SOURCE_BRANCH_NO);
    }

    @Override
    public String getSH_DestBranchNo() {
      return getSysHeaderData(DEST_BRANCH_NO);
    }

    @Override
    public String getSH_ModuleId() {
      return getSysHeaderData(MODULE_ID);
    }

    @Override
    public String getSH_UserLang() {
      return getSysHeaderData(USER_LANG);
    }

    @Override
    public String getSH_CoreSeqNo() {
      return getSysHeaderData(CORE_SEQ_NO);
    }

    @Override
    public String getSH_TellerSeqNo() {
      return getSysHeaderData(TELLER_SEQ_NO);
    }

    @Override
    public String getSH_ApprFlag() {
      return getSysHeaderData(APPR_FLAG);
    }

    @Override
    public String getSH_ProgramId() {
      return getSysHeaderData(PROGRAM_ID);
    }

    @Override
    public String getSH_AuthFlag() {
      return getSysHeaderData(AUTH_FLAG);
    }

    @Override
    public String getSH_TranType() {
      return getSysHeaderData(TRAN_TYPE);
    }

    @Override
    public String getSH_ApprUserId() {
      return getSysHeaderData(APPR_USER_ID);
    }

    @Override
    public String getSH_AuthUserId() {
      return getSysHeaderData(AUTH_USER_ID);
    }

    @Override
    public String getSH_AuthPassword() {
      return getSysHeaderData(AUTH_PASSWORD);
    }

    @Override
    public String getSH_ReversalTranType() {
      return getSysHeaderData(REVERSAL_TRAN_TYPE);
    }

    @Override
    public String getSH_FilePath() {
      return getSysHeaderData(FILE_PATH);
    }

    @Override
    public String getSH_RetStatus() {
      return getSysHeaderData(RET_STATUS);
    }

    @Override
    public Array getSH_Ret() {
      return getArrayData(visiting.getSysHeader().getData(SYS_HEAD).getStruct(), RET);
    }

    @Override
    public String getSH_RetCode() {
      return getSysHeaderData(RET_CODE);
    }

    @Override
    public String getSH_RetMsg() {
      return getSysHeaderData(RET_MSG);
    }

    @Override
    public String getSH_MacValue() {
      return getSysHeaderData(MAC_VALUE);
    }

    @Override
    public String getAH_PgupOrPgdn() {
      return getAppHeaderData(PGUP_OR_PGDN);
    }

    @Override
    public String getAH_TotalNum() {
      return getAppHeaderData(TOTAL_NUM);
    }

    @Override
    public String getAH_CurrentNum() {
      return getAppHeaderData(CURRENT_NUM);
    }

    @Override
    public String getAH_PageStart() {
      return getAppHeaderData(PAGE_START);
    }

    @Override
    public String getAH_PageEnd() {
      return getAppHeaderData(PAGE_END);
    }

    @Override
    public String getAH_TotalRows() {
      return getAppHeaderData(TOTAL_ROWS);
    }

    @Override
    public String getAH_TotalPages() {
      return getAppHeaderData(TOTAL_PAGES);
    }

    @Override
    public String getAH_TotalFlag() {
      return getAppHeaderData(TOTAL_FLAG);
    }

    @Override
    public Array getLH_Ret() {
      return getArrayData(visiting.getLocalHeader().getData(LOCAL_HEAD).getStruct(), RET);
    }

    @Override
    public String getLH_RetCode() {
      return getLocalHeaderData(RET_CODE);
    }

    @Override
    public String getLH_RetMsg() {
      return getLocalHeaderData(RET_MSG);
    }

    private Array getArrayData(InternalStruct struct, String name) {
      if (struct == null) {
        return null;
      }
      InternalData id = struct.getData(name);
      if (id.getDataType() != DataType.Array) {
        throw new PlatformException("Not a Array");
      }
      return Array.create(id);
    }

    private String getStringData(InternalStruct struct, String name) {
      if (struct == null) {
        return null;
      }
      InternalData field = struct.getData(name);
      if (field != null) {
        return field.getValue();
      }
      return null;
    }

    private String getSysHeaderData(String code) {
      return getStringData(visiting.getSysHeader().getData(SYS_HEAD).getStruct(), code);
    }

    private String getAppHeaderData(String code) {
      return getStringData(visiting.getAppHeader().getData(APP_HEAD).getStruct(), code);
    }

    private String getLocalHeaderData(String code) {
      return getStringData(visiting.getLocalHeader().getData(LOCAL_HEAD).getStruct(), code);
    }

    @Override
    public Field getField(String name) {
      InternalData id = visiting.getBody().getData(name);
      if (id != null) {
        if (id.getDataType() == DataType.Field) {
          return Field.create(id);
        } else {
          throw new PlatformException("Wrong Data Type");
        }
      }
      return null;
    }

    @Override
    public Array getArray(String name) {
      InternalData id = visiting.getBody().getData(name);
      if (id != null) {
        if (id.getDataType() == DataType.Array) {
          return Array.create(id);
        } else {
          throw new PlatformException("Wrong Data Type");
        }
      }
      return null;
    }
  }

  public interface Builder {

    ESBMessage build();

    Builder setSH_ServiceCode(String value);

    Builder setSH_MessageType(String value);

    Builder setSH_MessageCode(String value);

    Builder setSH_TranCode(String value);

    Builder setSH_TranMode(String value);

    Builder setSH_SourceType(String value);

    Builder setSH_BranchId(String value);

    Builder setSH_ConsumerId(String value);

    Builder setSH_UserId(String value);

    Builder setSH_TranDate(String value);

    Builder setSH_TranTimestamp(String value);

    Builder setSH_ServerId(String value);

    Builder setSH_WsId(String value);

    Builder setSH_SeqNo(String value);

    Builder setSH_SourceBranchNo(String value);

    Builder setSH_DestBranchNo(String value);

    Builder setSH_ModuleId(String value);

    Builder setSH_UserLang(String value);

    Builder setSH_CoreSeqNo(String value);

    Builder setSH_TellerSeqNo(String value);

    Builder setSH_ApprFlag(String value);

    Builder setSH_ProgramId(String value);

    Builder setSH_AuthFlag(String value);

    Builder setSH_TranType(String value);

    Builder setSH_ApprUserId(String value);

    Builder setSH_AuthUserId(String value);

    Builder setSH_AuthPassword(String value);

    Builder setSH_ReversalTranType(String value);

    Builder setSH_FilePath(String value);

    Builder setSH_RetStatus(String value);


    Builder addSH_Ret(String code, String msg);

//    Builder setSH_RetCode(String value);
//
//    Builder setSH_RetMsg(String value);

    Builder setSH_MacValue(String value);

    Builder setAH_PgupOrPgdn(String value);

    Builder setAH_TotalNum(String value);

    Builder setAH_CurrentNum(String value);

    Builder setAH_PageStart(String value);

    Builder setAH_PageEnd(String value);

    Builder setAH_TotalRows(String value);

    Builder setAH_TotalPages(String value);

    Builder setAH_TotalFlag(String value);

    Builder addLH_Ret(String code, String msg);

    Builder addLH_RetNullable();

    Builder setField(Field field);

    Builder setArray(Array array);
  }

  private static class BuilderImpl implements Builder {

    private ESBMessage building;
    private InternalData _data_SYS_HEAD = null;
    private InternalData _data_APP_HEAD = null;
    private InternalData _data_LOCAL_HEAD = null;

    private BuilderImpl() {
      building = new ESBMessage();
    }

    @Override
    public ESBMessage build() {
      return building;
    }

    @Override
    public Builder setSH_ServiceCode(String value) {
      setSysHeaderData(SERVICE_CODE, value);
      return this;
    }

    @Override
    public Builder setSH_MessageType(String value) {
      setSysHeaderData(MESSAGE_TYPE, value);
      return this;
    }

    @Override
    public Builder setSH_MessageCode(String value) {
      setSysHeaderData(MESSAGE_CODE, value);
      return this;
    }

    @Override
    public Builder setSH_TranCode(String value) {

      setSysHeaderDataNullable(TRAN_CODE, value);
      return this;
    }

    @Override
    public Builder setSH_TranMode(String value) {
      setSysHeaderDataNullable(TRAN_MODE, value);
      return this;
    }

    @Override
    public Builder setSH_SourceType(String value) {
      setSysHeaderData(SOURCE_TYPE, value);
      return this;
    }

    @Override
    public Builder setSH_BranchId(String value) {
      setSysHeaderData(BRANCH_ID, value);
      return this;
    }

    @Override
    public Builder setSH_ConsumerId(String value) {
      setSysHeaderDataNullable(CONSUMER_ID, value);
      return this;
    }

    @Override
    public Builder setSH_UserId(String value) {
      setSysHeaderData(USER_ID, value);
      return this;
    }

    @Override
    public Builder setSH_TranDate(String value) {
      setSysHeaderData(TRAN_DATE, value);
      return this;
    }

    @Override
    public Builder setSH_TranTimestamp(String value) {
      setSysHeaderData(TRAN_TIMESTAMP, value);
      return this;
    }

    @Override
    public Builder setSH_ServerId(String value) {
      setSysHeaderData(SERVER_ID, value);
      return this;
    }

    @Override
    public Builder setSH_WsId(String value) {
      setSysHeaderDataNullable(WS_ID, value);
      return this;
    }

    @Override
    public Builder setSH_SeqNo(String value) {
      setSysHeaderData(SEQ_NO, value);
      return this;
    }

    @Override
    public Builder setSH_SourceBranchNo(String value) {
      setSysHeaderDataNullable(SOURCE_BRANCH_NO, value);
      return this;
    }

    @Override
    public Builder setSH_DestBranchNo(String value) {
      setSysHeaderDataNullable(DEST_BRANCH_NO, value);
      return this;
    }

    @Override
    public Builder setSH_ModuleId(String value) {
      setSysHeaderDataNullable(MODULE_ID, value);
      return this;
    }

    @Override
    public Builder setSH_UserLang(String value) {
      setSysHeaderData(USER_LANG, value);
      return this;
    }

    @Override
    public Builder setSH_CoreSeqNo(String value) {
      setSysHeaderDataNullable(CORE_SEQ_NO, value);
      return this;
    }

    @Override
    public Builder setSH_TellerSeqNo(String value) {
      setSysHeaderDataNullable(TELLER_SEQ_NO, value);
      return this;
    }

    @Override
    public Builder setSH_ApprFlag(String value) {
      setSysHeaderDataNullable(APPR_FLAG, value);
      return this;
    }

    @Override
    public Builder setSH_ProgramId(String value) {
      setSysHeaderDataNullable(PROGRAM_ID, value);
      return this;
    }

    @Override
    public Builder setSH_AuthFlag(String value) {
      setSysHeaderDataNullable(AUTH_FLAG, value);
      return this;
    }

    @Override
    public Builder setSH_TranType(String value) {
      setSysHeaderDataNullable(TRAN_TYPE, value);
      return this;
    }

    @Override
    public Builder setSH_ApprUserId(String value) {
      setSysHeaderDataNullable(APPR_USER_ID, value);
      return this;
    }

    @Override
    public Builder setSH_AuthUserId(String value) {
      setSysHeaderDataNullable(AUTH_USER_ID, value);
      return this;
    }

    @Override
    public Builder setSH_AuthPassword(String value) {
      setSysHeaderDataNullable(AUTH_PASSWORD, value);
      return this;
    }

    @Override
    public Builder setSH_ReversalTranType(String value) {
      setSysHeaderDataNullable(REVERSAL_TRAN_TYPE, value);
      return this;
    }

    @Override
    public Builder setSH_FilePath(String value) {
      setSysHeaderDataNullable(FILE_PATH, value);
      return this;
    }

    @Override
    public Builder setSH_RetStatus(String value) {
      setSysHeaderData(RET_STATUS, value);
      return this;
    }

    @Override
    public Builder addSH_Ret(String code, String msg) {
      addRet(getData_SYS_HEAD().getStruct(), code, msg);
      return this;
    }

//    @Override
//    public Builder setSH_RetCode(String value) {
//      setSysHeaderData(RET_CODE, value);
//      return this;
//    }
//
//    @Override
//    public Builder setSH_RetMsg(String value) {
//      setSysHeaderData(RET_MSG, value);
//      return this;
//    }


    @Override
    public Builder setSH_MacValue(String value) {
      setSysHeaderData(MAC_VALUE, value);
      return this;
    }

    @Override
    public Builder setAH_PgupOrPgdn(String value) {
      setAppHeaderData(PGUP_OR_PGDN, value);
      return this;
    }

    @Override
    public Builder setAH_TotalNum(String value) {
      setAppHeaderDataNullable(TOTAL_NUM, value);
      return this;
    }

    @Override
    public Builder setAH_CurrentNum(String value) {
      setAppHeaderDataNullable(CURRENT_NUM, value);
      return this;
    }

    @Override
    public Builder setAH_PageStart(String value) {
      setAppHeaderDataNullable(PAGE_START, value);
      return this;
    }

    @Override
    public Builder setAH_PageEnd(String value) {
      setAppHeaderDataNullable(PAGE_END, value);
      return this;
    }

    @Override
    public Builder setAH_TotalRows(String value) {
      setAppHeaderDataNullable(TOTAL_ROWS, value);
      return this;
    }

    @Override
    public Builder setAH_TotalPages(String value) {
      setAppHeaderDataNullable(TOTAL_PAGES, value);
      return this;
    }

    @Override
    public Builder setAH_TotalFlag(String value) {
      setAppHeaderDataNullable(TOTAL_FLAG, value);
      return this;
    }

    @Override
    public Builder addLH_Ret(String code, String msg) {
      addRet(getData_LOCAL_HEAD().getStruct(), code, msg);
      return this;
    }

    @Override
    public Builder addLH_RetNullable() {
      getData_LOCAL_HEAD().getStruct();
      return this;
    }

    private void addRet(InternalStruct struct, String code, String msg) {
      InternalData id = struct.getData(RET);
      if (id == null) {
        id = InternalData.createArrayData(RET);
        struct.addData(id);
      }
      InternalStruct is = new InternalStruct();
      is.addData(InternalData.createStringField(RET_CODE, getDataLength(RET_CODE), code));
      is.addData(InternalData.createStringField(RET_MSG, getDataLength(RET_MSG), msg));
      id.getArray().addStruct(is);
    }

    private InternalData getData_SYS_HEAD() {
      if (_data_SYS_HEAD == null) {
        _data_SYS_HEAD = InternalData.createStructData(SYS_HEAD);
        building.getSysHeader().addData(_data_SYS_HEAD);
      }
      return _data_SYS_HEAD;
    }

    private void setHeaderData(InternalStruct struct, String code, String value) {
      if (code == null || value == null) {
        throw new PlatformException("Name or Value of Header Data can't be null");
      }
      int dataLength = getDataLength(code);
      if (value.length() > dataLength) {
        throw new PlatformException("Length of Value exceeds Max. Length");
      }
      struct.addData(InternalData.createStringField(code, getDataLength(code), value));
    }

    private void setSysHeaderData(String code, String value) {
      setHeaderData(getData_SYS_HEAD().getStruct(), code, value);
    }

    private void setSysHeaderDataNullable(String code, String value) {
      setHeaderData(getData_SYS_HEAD().getStruct(), code, StringUtils.isNotBlank(value) ? value : StringUtils.EMPTY);
    }

    private InternalData getData_APP_HEAD() {
      if (_data_APP_HEAD == null) {
        _data_APP_HEAD = InternalData.createStructData(APP_HEAD);
        building.getAppHeader().addData(_data_APP_HEAD);
      }
      return _data_APP_HEAD;
    }

    private void setAppHeaderData(String code, String value) {
      setHeaderData(getData_APP_HEAD().getStruct(), code, value);
    }

    private void setAppHeaderDataNullable(String code, String value) {
      setHeaderData(getData_APP_HEAD().getStruct(), code, StringUtils.isNotBlank(value) ? value : StringUtils.EMPTY);
    }


    private InternalData getData_LOCAL_HEAD() {
      if (_data_LOCAL_HEAD == null) {
        _data_LOCAL_HEAD = InternalData.createStructData(LOCAL_HEAD);
        building.getLocalHeader().addData(_data_LOCAL_HEAD);
      }
      return _data_LOCAL_HEAD;
    }

    private void setLocalHeaderData(String code, String value) {
      setHeaderData(getData_LOCAL_HEAD().getStruct(), code, value);
    }

    @Override
    public Builder setField(Field field) {
      building.getBody().addData(InternalData.create(field));
      return this;
    }

    @Override
    public Builder setArray(Array array) {
      building.getBody().addData(InternalData.create(array));
      return this;
    }
  }

  private InternalStruct sysHeader = new InternalStruct();
  private InternalStruct appHeader = new InternalStruct();
  private InternalStruct localHeader = new InternalStruct();
  private InternalStruct body = new InternalStruct();
  private Visitor visitor = null;

  @Override
  public ESBMessage clone() {

    ESBMessage esbMessage = new ESBMessage();
    esbMessage.sysHeader = this.getSysHeader();
    esbMessage.appHeader = this.getAppHeader();
    esbMessage.localHeader = this.getLocalHeader();
    esbMessage.body = this.getBody();
    return esbMessage;
  }


  @Override
  public ESBMessage empty() {
    return newResponseBuilder().build();
  }

  InternalStruct getSysHeader() {
    return sysHeader;
  }

  InternalStruct getAppHeader() {
    return appHeader;
  }

  InternalStruct getLocalHeader() {
    return localHeader;
  }

  InternalStruct getBody() {
    return body;
  }

  public Visitor getVisitor() {
    if (visitor == null) {
      visitor = new VisitorImpl(this);
    }
    return visitor;
  }

  public static Builder newBuilder() {
    return new BuilderImpl();
  }

  public Builder newResponseBuilder() {
    Visitor v = getVisitor();
    BuilderImpl builder = new BuilderImpl();

    builder.setSH_BranchId(v.getSH_BranchId());
    builder.setSH_SourceBranchNo(v.getSH_SourceBranchNo());
    builder.setSH_DestBranchNo(v.getSH_DestBranchNo());
    builder.setSH_ServiceCode(v.getSH_ServiceCode());
    return builder;
  }
}
