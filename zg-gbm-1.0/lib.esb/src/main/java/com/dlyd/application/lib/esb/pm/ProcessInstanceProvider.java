//package com.dlyd.application.lib.esb.pm;
//
//import org.noc.hccp.platform.common.AppLog;
//import org.noc.hccp.platform.common.PlatformException;
//import org.noc.hccp.platform.common.msg.PlatformMessage;
//import org.noc.hccp.platform.common.msg.PlatformMessage.E;
//import org.noc.hccp.platform.common.msg.PlatformMessage.M;
//import org.noc.hccp.platform.service.PlatformMessageDrivenProcess;
//import org.noc.hccp.platform.service.PlatformMessageDrivenProcessProvider;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Optional;
//
//public class ProcessInstanceProvider extends PlatformMessageDrivenProcessProvider {
//
//
//  @Autowired
//
//  @Override
//  protected String resolveBeanName(PlatformMessage source) {
//    // TODO Mapping from ApplicationID+ServiceID+Code in PlatformMessage to bean name of the Process Instance Bean
//    PlatformMessage.Visitor visitor = source.visitor();
//    String primaryApplicationId = visitor.getPrimaryApplicationId();
//    String primaryServiceId = visitor.getPrimaryServiceId();
//    String primaryServiceCode = visitor.getPrimaryServiceCode();
//    AppLog.logger.info("primaryApplicationId:{},primaryServiceId:{},primaryServiceCode:{}",primaryApplicationId,primaryServiceId,primaryServiceCode);
//
//    Optional<E> optSysHeaderMap = source.visitor().getRequest().get(PMC.SYS_HEADER);
//    if (!optSysHeaderMap.isPresent()) {
//      throw new PlatformException("SYS_HEADER not found");
//    }
//    M sysHeaderMap = (M) optSysHeaderMap.get();
//    M sysHead = sysHeaderMap.getMap("SYS_HEAD");
//    String sourceType = sysHead.getValue("SOURCE_TYPE", true);
//    String messageType = sysHead.getValue("MESSAGE_TYPE", true);
//    String messageCode = sysHead.getValue("MESSAGE_CODE", true);
//    AppLog.logger.info("交易请求渠道:[{}]，消息类型[{}],消息编码[{}]，平台交易代码:[{}]{}", sourceType, messageType, messageCode);
//    //暂时固定返回WY001
//    return "WY001";
//  }
//
//  @Override
//  public void initializeProcess(PlatformMessage source, PlatformMessageDrivenProcess process) {
////    AppLog.logger.info(source.builder().setde);
////    source.builder().setDestinationServiceCode();return "DEMO";
//    source.builder().setDestinationApplicationId("SI-PAY");
//    source.builder().setDestinationServiceId("SI-PAY-ESB-PROC");
//    source.builder().setDestinationServiceCode("WY001");
////    AppLog.logger.info("==============================initializeProcess");
//  }
//
//}
