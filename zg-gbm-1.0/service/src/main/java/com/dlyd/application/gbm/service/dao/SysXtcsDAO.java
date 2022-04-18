package com.dlyd.application.gbm.service.dao;

import com.dlyd.application.gbm.service.domain.SysXtcsDO;
import org.springframework.dao.DataAccessException;

public interface SysXtcsDAO {
  boolean insert(SysXtcsDO sysXtcsDO);

  boolean delete(String xtbz);

  boolean update(String xtbz, SysXtcsDO sysXtcsDO);

  boolean updateXtrq(String xtbz, String xtrq);

  SysXtcsDO select(String xtbz) throws DataAccessException;
}
