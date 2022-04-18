package com.dlyd.application.lib.esb.msg11;

import com.dlyd.application.lib.esb.msg11.ESBMessage.Visitor;
import java.io.FileInputStream;
import org.apache.commons.io.IOUtils;
import org.noc.hccp.platform.common.msg.ByteMessageFactory;

public class TestESBMessage {

  public static void main(String[] args) {
    try {
      ByteMessageFactory bmf = new MessageFactoryImpl();
      byte[] bytes = IOUtils.toByteArray(new FileInputStream("test01.xml"));
      ESBMessage message = (ESBMessage) bmf.toMessage(bytes);
      Visitor v = message.getVisitor();

      System.out.println(v.getSH_SourceBranchNo());

      System.out.println(v.getAH_TotalNum());

      System.out.println(v.getField("USER_ID"));

      System.out.println("----------------------------------------------------------");
      byte[] toWrite = bmf.toBytes(message);
      System.out.println(new String(toWrite,"UTF-8"));

      System.out.println("----------------------------------------------------------");
      ESBMessage.Builder builder = ESBMessage.newBuilder();
      builder.setSH_ApprFlag("T");
      builder.setSH_ApprUserId("TEST_APPR_USER_ID");
      builder.addSH_Ret("SH_RET_CODE1", "SH_RET_MSG1");
      builder.addSH_Ret("SH_RET_CODE2", "SH_RET_MSG2");
      builder.setAH_CurrentNum("TEST_CN");
      builder.addLH_Ret("LH_RET_CODE1", "LH_RET_MSG1");
      builder.addLH_Ret("LH_RET_CODE2", "LH_RET_MSG2");
      builder.setField(Field.createStringField("BODY_FIELD_1", 20, "BODY_FIELD_1_VALUE"));
      builder.setField(Field.createStringField("BODY_FIELD_2", 10, "BODY_FIELD_2_VALUE"));

      toWrite = bmf.toBytes(builder.build());
      System.out.println(new String(toWrite,"UTF-8"));

      ESBMessage message1 = (ESBMessage) bmf.toMessage(toWrite);
      Visitor v1 = message1.getVisitor();
      System.out.println(v1.getSH_ApprFlag());
      System.out.println(v1.getSH_ApprUserId());
      System.out.println(v1.getAH_CurrentNum());
      System.out.println(v1.getField("BODY_FIELD_1"));
      System.out.println(v1.getField("BODY_FIELD_2"));
      Array array = v1.getSH_Ret();
      for(Struct s : array.getStructs()) {
        System.out.println(String.format("SH_RET: [%s] [%s]", s.getField("RET_CODE").getValue(), s.getField("RET_MSG").getValue()));
      }
      array = v1.getLH_Ret();
      for(Struct s : array.getStructs()) {
        System.out.println(String.format("LH_RET: [%s] [%s]", s.getField("RET_CODE").getValue(), s.getField("RET_MSG").getValue()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
