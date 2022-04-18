package com.dlyd.application.lib.cryptography;

import java.security.PublicKey;

public interface PublicKeyProvider extends KeyProvider {

  @Override
  PublicKey getKey();
}
