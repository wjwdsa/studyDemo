package com.dlyd.application.gbm.test;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import com.dlyd.application.gbm.service.KtkjInfo;
import org.apache.commons.io.IOUtils;

import com.alibaba.fastjson.JSONArray;




/**
 * @Desc
 * @author 马权
 *
 * @Date 2020年10月29日 上午10:45:46
 */
public class Client {

  private static String pubKeyRSA="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAh72JtV13Y9OuLCtyAQ3bud2n+IbvW8ovzfCIvOW0OD4lc3vhDevAakL24bzb7LB79SBa8CcOlwX8LuZDuWaeeL1Sh8E1umwqOxakQvy871VrV8gDeVP9gC7s1vz//0hfIovNjL/m1EMNyyLqP5724X9e+NKEp/37P31D/3qolxlUPzoF4RzhDBcMX9WDGE4nwVpaYs8uYESFmVfYElaP2YXKJW4ZeKvcYoMIGj22NEFdWxv3AVVGj60BcKSlg5EeHjQrTtEs+eSmTj53eytXMVUZ1hqyENmxbV6uEJ7FvIBBAFGIYnYdW+HHgVR0TEXB79eEJnTWCxx/mnMOWDfzawIDAQAB";
  private static String pubKeyAES="XBknsOEQ6yg7oEuSUVnkCg==";//Aes加密秘钥
  public static void main(String[] args) throws Exception {
    List<KtkjInfo> r=new ArrayList<>();
    KtkjInfo kt = new KtkjInfo();
    kt.setBalance("25");
    kt.setBankId("126622052189");
    kt.setCounterBankId("222222");
    kt.setCounterBankName("中国人民银行");
    kt.setCurrency("人名币");
    kt.setHandFee("0.265");
    kt.setLoanSign("借");
    kt.setMark("1");
    kt.setRemarks("sadfa大了1");
    kt.setTransAmount("1526895.56");
    kt.setTotal("5554441.59");
    kt.setTransHour("2020-09-08 15:36:10");
    kt.setTransType("转账");
    kt.setVoucherNumber("456489434684945646448691");
    kt.setPzNumber("4564894346849456464486");
    kt.setPayeeName("收款方名称");
    kt.setUseFunds("资金用途");
    kt.setVirtualBankId("0");
    r.add(kt);
    kt = new KtkjInfo();
    kt.setBalance("25");
    kt.setBankId("1266220521891");
    kt.setCounterBankId("222222");
    kt.setCounterBankName("中国人民银行111");
    kt.setCurrency("人名币");
    kt.setHandFee("0.265");
    kt.setLoanSign("借");
    kt.setMark("1");
    kt.setRemarks("sadfa大了1");
    kt.setTransAmount("1526895.56");
    kt.setTotal("5554441.59");
    kt.setTransHour("2020-09-08 15:36:10");
    kt.setTransType("转账");
    kt.setVoucherNumber("45648943468494564644881");
    kt.setPzNumber("4564894346849456464486");
    kt.setPayeeName("收款方名称");
    kt.setUseFunds("资金用途");
    kt.setVirtualBankId("0");
    r.add(kt);
    kt = new KtkjInfo();
    kt.setBalance("25");
    kt.setBankId("126622052189");
    kt.setCounterBankId("222222");
    kt.setCounterBankName("中国人民银行111");
    kt.setCurrency("人名币");
    kt.setHandFee("0.265");
    kt.setLoanSign("借");
    kt.setMark("1");
    kt.setRemarks("sadfa大了1");
    kt.setTransAmount("1526895.56");
    kt.setTotal("5554441.59");
    kt.setTransHour("2020-09-08 15:36:10");
    kt.setTransType("转账");
    kt.setVoucherNumber("45648943468494564644869");
    kt.setPzNumber("4564894346849456464486");
    kt.setPayeeName("收款方名称");
    kt.setUseFunds("资金用途");
    kt.setVirtualBankId("0");
    r.add(kt);
    //本地测试使用报文
    String wsdl = "http://118.24.88.55:8026/axis2/services/MyService?wsdl";
    int timeout = 1000000;
    //组报文
    String soap= "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:axis=\"http://axis2.ktkj.com\">"+
            "<soapenv:Header/>"+
            "<soapenv:Body>"+
            "<axis:ktkjBankFlowSaveCS>"+
            " <axis:bankFlow>"+AES.encryptAES(JSONArray.toJSONString(r),pubKeyAES)+"</axis:bankFlow>"+
            "<axis:pubKeyAes>"+RSA.encrypt(pubKeyAES, pubKeyRSA)+"</axis:pubKeyAes>"+
            "<axis:idCard>"+AES.encryptAES("43F64DD0BA654566888ddad84C8BE0807AF3B18",pubKeyAES)+"</axis:idCard>"+
            "</axis:ktkjBankFlowSaveCS>"+
            "</soapenv:Body>"+
            "</soapenv:Envelope>";
    System.out.println(soap);
    // HttpClient发送SOAP请求
    System.out.println("HttpClient 发送SOAP请求");
//    HttpClient client = new HttpClient();
//    PostMethod postMethod = new PostMethod(wsdl);
//    // 设置连接超时
//    client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
//    // 设置读取时间超时
//    client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
//    // 然后把Soap请求数据添加到PostMethod中
//    RequestEntity requestEntity = new StringRequestEntity(soap, "text/xml", "UTF-8");
//    // 设置请求头部，否则可能会报 “no SOAPAction header” 的错误
//    postMethod.setRequestHeader("SOAPAction","");
//    //  设置请求体
//    postMethod.setRequestEntity(requestEntity);
//    int status = client.executeMethod(postMethod);
//    // 打印请求状态码
//    System.out.println("status:" + status);
//    //  获取响应体输入流
//    InputStream is = postMethod.getResponseBodyAsStream();
//    String result = IOUtils.toString(is);
//    Document dc = strXmlToDocument(result);
//    //  获取请求结果字符串
//    Element root = dc.getRootElement();
//    System.out.println(root.getName());
//    System.out.println("result: " + result);
//    System.out.println(result);
  }

//  public static Document strXmlToDocument(String parseStrXml){
//    Document document = null;
//    try {
//      document = DocumentHelper.parseText(parseStrXml);
//      Element root = document.getRootElement();
//      List<Element> list = root.elements();
//      getElement(list);
//    } catch (DocumentException e) {
//      e.printStackTrace();
//    }
//    return document;
//  }
//  private static void getElement(List<Element> sonElemetList) {
////       Map<String,String> map = new HashMap<String, String>();
//    for (Element sonElement : sonElemetList) {
//      if (sonElement.elements().size() != 0) {
//        System.out.println(sonElement.getName() + ":");
//        getElement(sonElement.elements());
//      }else{
//        System.out.println(sonElement.getName() + ":"+ sonElement.getText());
//      }
//
//    }
//  }

}
