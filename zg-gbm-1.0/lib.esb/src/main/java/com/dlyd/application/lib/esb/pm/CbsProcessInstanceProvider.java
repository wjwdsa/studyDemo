package com.dlyd.application.lib.esb.pm;

import org.noc.hccp.platform.common.AppLog;
import org.noc.hccp.platform.common.PlatformException;
import org.noc.hccp.platform.common.msg.PlatformMessage;
import org.noc.hccp.platform.common.msg.PlatformMessage.E;
import org.noc.hccp.platform.common.msg.PlatformMessage.M;
import org.noc.hccp.platform.service.PlatformMessageDrivenProcess;
import org.noc.hccp.platform.service.PlatformMessageDrivenProcessProvider;

import java.util.Optional;

public class CbsProcessInstanceProvider extends PlatformMessageDrivenProcessProvider {


  @Override
  protected String resolveBeanName(PlatformMessage source) {
    // TODO Mapping from ApplicationID+ServiceID+Code in PlatformMessage to bean name of the Process Instance Bean
    PlatformMessage.Visitor visitor = source.visitor();
    String destinationApplicationId = visitor.getDestinationApplicationId();
    String destinationServiceId = visitor.getDestinationServiceId();
    String destinationServiceCode = visitor.getDestinationServiceCode();
    AppLog.logger.info("destinationApplicationId:[{}],destinationServiceId:[{}],destinationServiceCode:[{}]", destinationApplicationId, destinationServiceId, destinationServiceCode);
    return destinationServiceCode;
  }

  @Override
  public void initializeProcess(PlatformMessage source, PlatformMessageDrivenProcess process) {
    AppLog.logger.info("==============================initializeProcess");
  }

}
