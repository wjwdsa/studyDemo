package com.dlyd.application.lib.cryptography;

import java.security.cert.Certificate;

public interface CertificateProvider {
  Certificate getCertificate();
}
