package com.dlyd.application.gbm.service.service.impl;

import com.dlyd.application.gbm.service.dao.SysXtcsDAO;
import com.dlyd.application.gbm.service.domain.SysXtcsDO;
import com.dlyd.application.gbm.service.service.SysXtcsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysXtcsServiceImpl implements SysXtcsService, InitializingBean {
  @Autowired
  private SysXtcsDAO sysXtcsDAO;

  @Override
  public SysXtcsDO selectOne(String xtbz) {
    return sysXtcsDAO.select(xtbz);
  }

  @Override
  public boolean updateXtrq(String xtbz, String xtrq) {
    return sysXtcsDAO.updateXtrq(xtbz,xtrq);
  }

  @Override
  public void afterPropertiesSet() throws Exception {

  }
}
