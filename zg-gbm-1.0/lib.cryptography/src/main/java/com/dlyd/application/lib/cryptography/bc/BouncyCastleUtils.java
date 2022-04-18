package com.dlyd.application.lib.cryptography.bc;

import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class BouncyCastleUtils {

  private BouncyCastleUtils() {
  }

  synchronized public static void registerProvider() {
    if (null == Security.getProvider(BouncyCastleProvider.PROVIDER_NAME)) {
      Security.addProvider(new BouncyCastleProvider());
    }
  }
}
