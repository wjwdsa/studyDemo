package com.dlyd.application.gbm.service.dao;

import com.dlyd.application.gbm.service.domain.SysTranCodeDO;

public interface SysTranCodeDAO {
  boolean insert(SysTranCodeDO sysTranCodeDO);

  SysTranCodeDO select(String sourceType, String messageType, String messageCode);
}
