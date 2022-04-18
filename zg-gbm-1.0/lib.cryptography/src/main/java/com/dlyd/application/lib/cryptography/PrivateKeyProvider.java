package com.dlyd.application.lib.cryptography;

import java.security.PrivateKey;

public interface PrivateKeyProvider extends KeyProvider {

  @Override
  PrivateKey getKey();
}
