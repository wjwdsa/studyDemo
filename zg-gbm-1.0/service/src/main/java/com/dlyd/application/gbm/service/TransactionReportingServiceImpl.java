package com.dlyd.application.gbm.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONArray;
import com.dlyd.application.gbm.service.dao.*;
import com.dlyd.application.lib.cryptography.RSAEncryption;
import com.dlyd.application.lib.webservice.WsMessageException;
import org.noc.hccp.platform.common.PLOG;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//@Service
public class TransactionReportingServiceImpl implements TransactionReportingService, InitializingBean {

  private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";

  private MessageFactory soapMessageFactory;
  private SecureRandom keySecureRandom;
  private String bankId;
  private RSAEncryption rsaEncryption;
  private String aesKey = null;

  @Autowired
  private SysXtcsDAO sysXtcsDAO;
  @Autowired
  private ReportingAccountDAO reportingAccountDAO;
  @Autowired
  private ReportingBatchDAO reportingBatchDAO;
  @Autowired
  private ReportingDetailDAO reportingDetailDao;
  @Autowired
  private ReportingStatusDAO reportingStatusDao;


  public TransactionReportingServiceImpl() throws SOAPException {
    soapMessageFactory = MessageFactory.newInstance();
    keySecureRandom = ServiceUtils.createSecureRandomWithTimestamp();
  }

  private String createAESBase64Key() {
    // 16位数字的AES密钥，8位工作日期+最多8位随机数字
    long vl = getWorkingDate() * 100000000L + keySecureRandom.nextInt(100000000);
    String vs = String.format("%16d", vl);
    return Base64Utils.encodeToString(vs.getBytes(StandardCharsets.UTF_8));
  }

  private String createAESHexKey() {
    // 16位数字的AES密钥，8位工作日期+最多8位随机数字
    long vl = getWorkingDate() * 100000000L + keySecureRandom.nextInt(100000000);
    String vs = String.format("%16d", vl);
    return parseByte2HexStr(vs.getBytes(StandardCharsets.UTF_8));
  }

  private String createAESKey() {
    // 16位数字的AES密钥，8位工作日期+最多8位随机数字
    long vl = getWorkingDate() * 100000000L + keySecureRandom.nextInt(100000000);
    String vs = String.format("%16d", vl);
    return vs;
  }


  private static SecretKey createAESSecretKey(byte[] key) {
    try {
      KeyGenerator keygen = KeyGenerator.getInstance("AES");
      SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
      secureRandom.setSeed(key);
      PLOG.normal.debug(secureRandom.toString());
      keygen.init(128, secureRandom);
      SecretKey original_key = keygen.generateKey();
      byte[] raw = original_key.getEncoded();
      SecretKey key1 = new SecretKeySpec(raw, "AES");
      return key1;
    } catch (Throwable e) {
      PLOG.normal.error(e.getMessage(), e);
      throw new RuntimeException(e);
    }
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }

  public void setRsaEncryption(RSAEncryption rsaEncryption) {
    this.rsaEncryption = rsaEncryption;
  }

  @Override
  public void afterPropertiesSet() throws Exception {

  }

  private static int getWorkingDate() {
    LocalDate ld = LocalDate.now();
    ld = ld.minusDays(1);
    return ld.getYear() * 10000 + ld.getMonthValue() * 100 + ld.getDayOfMonth();
  }

  /*
  每日定时任务执行时，初始化当天的记录
   */
  @Override
  @Transactional
  public ReportingBatch initializeDailyReporting(String reportDate) throws ParseException {
    int date = Integer.parseInt(reportDate);
    ReportingStatus select = reportingStatusDao.select(date);
    PLOG.normal.debug(select);
    if (select!=null && ReportingStatus.STATUS_FAILED ==select.getStatus()){
      reportingStatusDao.delete(date);
      reportingBatchDAO.delete(date);
      reportingDetailDao.delete(date);
    }

    ReportingStatus status = new ReportingStatus();
    status.setDate(date);
    status.setStage(ReportingStatus.STAGE_LOADING);
    status.setStatus(ReportingStatus.STATUS_RUNNING);
    status.setCurrentBatchNo(1);
    ReportingBatch batch = new ReportingBatch();
    batch.setDate(status.getDate());
    batch.setNo(1);
    batch.setStatus(ReportingBatch.STATUS_RUNNING);
    reportingStatusDao.insert(status);
    reportingBatchDAO.insert(batch);
    return batch;
  }

  /*
  从核心获取交易明细后，登记交易明细
   */
  @Override
  @Transactional
  public void queryTransactionsSuccess(ReportingBatch batch, List<KtkjInfo> list) {
    reportingStatusDao.update(batch.getDate(), ReportingStatus.STAGE_REPORTING, ReportingStatus.STATUS_RUNNING);
    reportingDetailDao.insert(list, batch.getDate(), ReportingDetail.STAGE_LOADED, ReportingDetail.STATUS_COMPLETE, batch.getNo());
  }

  @Override
  @Transactional
  public void queryTransactionsFailed(ReportingBatch batch) {
    reportingStatusDao.update(batch.getDate(), ReportingStatus.STAGE_LOADING, ReportingStatus.STATUS_FAILED);
    reportingBatchDAO.end(batch.getDate(), batch.getNo(), ReportingBatch.STATUS_FAILED);
  }

  @Override
  @Transactional
  public boolean reportTransactionsSuccess(ReportingBatch batch, String result) {
    reportingStatusDao.update(batch.getDate(), ReportingStatus.STAGE_FINISH, ReportingStatus.STATUS_COMPLETE);
    reportingBatchDAO.end(batch.getDate(), batch.getNo(), ReportingBatch.STATUS_FINISH);
    reportingDetailDao.updateSuccess(batch);
    SecretKey aesSecretKey = createAESSecretKey(aesKey.getBytes());
    boolean success = true;
    try {

      String json = decryptAES(aesSecretKey, result);
      HashMap<String, Object> parse = (HashMap<String, Object>) JSONUtils.parse(json);
      List<HashMap> msg = (List<HashMap>) parse.get("msg");
      for (int i = 0; i < msg.size(); i++) {
        HashMap hashMap = msg.get(i);
        String code = (String) hashMap.get("codeName");
        String prompt = (String) hashMap.get("prompt");
        PLOG.normal.debug(hashMap.get("codeName"));
        PLOG.normal.debug(hashMap.get("prompt"));
        if (!"jy_200".equals(code)) {
          success = false;
        }
        List<HashMap> ktkjInfoList = (List<HashMap>) hashMap.get("ktkjInfos");
        if (ktkjInfoList != null) {
          for (HashMap ktkjInfo : ktkjInfoList) {
            String voucherNumber = (String) ktkjInfo.get("voucherNumber");
            reportingDetailDao.updateCodeAndPrompt(voucherNumber, code, prompt);
          }
        }
      }
    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
      PLOG.normal.error("反馈报文解密错误", e);
      success = false;
    } finally {
      return success;
    }

  }


  @Override
  @Transactional
  public boolean reportErrorTransactionsSuccess(String result) {
    SecretKey aesSecretKey = createAESSecretKey(aesKey.getBytes());
    boolean success = true;
    try {
      String json = decryptAES(aesSecretKey, result);
      HashMap<String, Object> parse = (HashMap<String, Object>) JSONUtils.parse(json);
      List<HashMap> msg = (List<HashMap>) parse.get("msg");
      for (int i = 0; i < msg.size(); i++) {
        HashMap hashMap = msg.get(i);
        String code = (String) hashMap.get("codeName");
        String prompt = (String) hashMap.get("prompt");
        PLOG.normal.debug(hashMap.get("codeName"));
        PLOG.normal.debug(hashMap.get("prompt"));
        if (!"jy_200".equals(code)) {
          success = false;
        }
        List<HashMap> ktkjInfoList = (List<HashMap>) hashMap.get("ktkjInfos");
        if (ktkjInfoList != null) {
          for (HashMap ktkjInfo : ktkjInfoList) {
            String voucherNumber = (String) ktkjInfo.get("voucherNumber");
            reportingDetailDao.updateCodeAndPrompt(voucherNumber, code, prompt);
          }
        }
      }
    } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException | UnsupportedEncodingException e) {
      PLOG.normal.error("反馈报文解密错误", e);
      success = false;
    } finally {
      return success;
    }

  }

  @Override
  @Transactional
  public void reportTransactionsFailed(ReportingBatch batch) {
    reportingStatusDao.update(batch.getDate(), ReportingStatus.STAGE_REPORTING, ReportingStatus.STATUS_FAILED);
    reportingBatchDAO.end(batch.getDate(), batch.getNo(), ReportingBatch.STATUS_FAILED);
  }

  String encryptAES(SecretKey secretAESKey, String content) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException { // ����
    byte[] aes = aes(content.getBytes(StandardCharsets.UTF_8), Cipher.ENCRYPT_MODE, secretAESKey);
    return parseByte2HexStr(aes);
  }

  static String decryptAES(SecretKey secretAESKey, String contentArray) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException { // ����
    byte[] parseHexStr2Byte = parseHexStr2Byte(contentArray);
    byte[] result = aes(parseHexStr2Byte, Cipher.DECRYPT_MODE, secretAESKey);
    return new String(result, StandardCharsets.UTF_8);
  }

  static byte[] aes(byte[] contentArray, int mode, SecretKey secretKey)
          throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
    cipher.init(mode, secretKey);
    byte[] result = cipher.doFinal(contentArray);
    return result;
  }

  static String parseByte2HexStr(byte buf[]) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < buf.length; i++) {
      String hex = Integer.toHexString(buf[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }
      sb.append(hex.toUpperCase());
    }
    return sb.toString();
  }

  static byte[] parseHexStr2Byte(String hexStr) {
    if (hexStr.length() < 1) {
      return null;
    }
    byte[] result = new byte[hexStr.length() / 2];
    for (int i = 0; i < hexStr.length() / 2; i++) {
      int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
      int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
      result[i] = (byte) (high * 16 + low);
    }
    return result;
  }

  // RSA公钥加密AES密钥
  String encryptRSA(String content) {
//    return parseByte2HexStr(rsaEncryption.publicKeyEncrypt(content.getBytes(StandardCharsets.UTF_8)));
    return Base64Utils.encodeToString(rsaEncryption.publicKeyEncrypt(content.getBytes(StandardCharsets.UTF_8)));
  }


  /*
  构建推送报文，无数据库操作
   */
  @Override
  public SOAPMessage buildReportingMessage(List<KtkjInfo> infoList) {
    try {
//      String base64AESKey = createAESBase64Key();
//      String hexAESKey = createAESHexKey();
      aesKey = createAESKey();
      SecretKey secretAESKey = createAESSecretKey(aesKey.getBytes());

      SOAPMessage soapMessage = soapMessageFactory.createMessage();
      SOAPPart soapPart = soapMessage.getSOAPPart();
      SOAPEnvelope soapEnvelope = soapPart.getEnvelope();
      soapEnvelope.addNamespaceDeclaration("ktkj", "http://axis2.ktkj.com");
      SOAPBody soapBody = soapEnvelope.getBody();
      SOAPElement seKtkjBankFlowSave = soapBody.addChildElement("ktkjBankFlowSaveCS","ktkj");
      SOAPElement seBankFlow = seKtkjBankFlowSave.addChildElement("bankFlow", "ktkj");
      seBankFlow.addTextNode(encryptAES(secretAESKey, JSONArray.toJSONString(infoList)));
      SOAPElement sePubKeyAes = seKtkjBankFlowSave.addChildElement("pubKeyAes", "ktkj");
      PLOG.normal.debug("aesKey:{}", aesKey);
      PLOG.normal.debug(encryptRSA(aesKey));
      sePubKeyAes.addTextNode(encryptRSA(aesKey));

      SOAPElement seIdCard = seKtkjBankFlowSave.addChildElement("idCard", "ktkj");
      PLOG.normal.debug("bankId:{}", bankId);
      seIdCard.addTextNode(encryptAES(secretAESKey, bankId));
      soapMessage.saveChanges();

      // 测试用跟踪 >>>
      ByteArrayOutputStream baosPrint = new ByteArrayOutputStream();
      PLOG.normal.debug("----------SOAP Request >>>-----------");
      PLOG.normal.debug(new String(baosPrint.toByteArray(), StandardCharsets.UTF_8));
      PLOG.normal.debug("----------SOAP Request <<<-----------");
      // 测试用跟踪 <<<

      return soapMessage;
    } catch (Throwable e) {
      PLOG.normal.debug(e.getMessage(), e);
      throw new WsMessageException(e);
    }
  }

  /*
  从响应报文中获取result（json格式字符串，参见接口文档）
   */
  @Override
  public String getReportingResponse(SOAPMessage response) {
    try {
      QName bodyName = new QName("http://axis2.ktkj.com", "ktkjBankFlowSaveCSResponse");
      QName returnName = new QName("http://axis2.ktkj.com", "return");
      SOAPBody soapBody = response.getSOAPBody();
      java.util.Iterator iterator = soapBody.getChildElements(bodyName);
      SOAPBodyElement bodyElement = (SOAPBodyElement) iterator.next();
      iterator = bodyElement.getChildElements(returnName);
      SOAPElement returnElement = (SOAPElement) iterator.next();
      String returnContent = returnElement.getValue();
      return returnContent;
    } catch (Throwable e) {
      throw new WsMessageException(e.getMessage(), e);
    }
  }

  @Override
  public List<ReportingAccount> listNormalReportingAccount() {
    return reportingAccountDAO.listNormal();
  }

  @Override
  public List<KtkjInfo> buildErrorReport(List<String> voucherNumbers) {
    List<ReportingDetail> reportingDetailList = reportingDetailDao.listByVoucherNumbers(voucherNumbers);
    List<KtkjInfo> ktkjInfoList = new ArrayList<>();
    for (ReportingDetail reportingDetail : reportingDetailList) {
      ktkjInfoList.add(reportingDetail.getInfo());
    }
    return ktkjInfoList;
  }
}
