package com.dlyd.application.gbm.service;

import java.security.SecureRandom;
import java.util.Date;

abstract class ServiceUtils {

  private ServiceUtils() {

  }

  static byte[] longToBytes(long l) {
    int lbs = Long.SIZE / Byte.SIZE;
    byte[] result = new byte[lbs];
    for (int i = lbs - 1; i >= 0; i--) {
      result[i] = (byte) (l & 0xFF);
      l >>= Byte.SIZE;
    }
    return result;
  }

  static SecureRandom createSecureRandomWithTimestamp() {
    byte[] seed = longToBytes(new Date().getTime());
    return new SecureRandom(seed);
  }
}
