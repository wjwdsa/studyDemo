package com.dlyd.application.gbm.service.service;

import com.dlyd.application.gbm.service.domain.SysXtcsDO;

public interface SysXtcsService {
  SysXtcsDO selectOne(String xtbz);

  boolean updateXtrq(String xtbz, String xtrq);
}
