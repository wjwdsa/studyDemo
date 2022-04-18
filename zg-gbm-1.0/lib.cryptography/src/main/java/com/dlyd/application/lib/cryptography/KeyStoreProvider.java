package com.dlyd.application.lib.cryptography;

import java.security.KeyStore;

public interface KeyStoreProvider {
  KeyStore getStore();
}
