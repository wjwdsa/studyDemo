package com.dlyd.application.lib.cryptography;

import java.security.Key;

public interface KeyProvider {
  <T extends Key> T getKey();
}
