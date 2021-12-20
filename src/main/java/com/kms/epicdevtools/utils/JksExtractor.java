package com.kms.epicdevtools.utils;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPublicKeySpec;

public class JksExtractor {

  public static final String JKS_FORMAT = "JKS";
  public static final String RSA_FORMAT = "RSA";

  public static KeyPair execute(String password, String keyAlias, InputStream fileStream) {
    try {
      KeyStore store = KeyStore.getInstance(JKS_FORMAT);
      store.load(fileStream, password.toCharArray());
      RSAPrivateCrtKey privateKey = (RSAPrivateCrtKey) store.getKey(keyAlias,
          password.toCharArray());

      Certificate certificate = store.getCertificate(keyAlias);
      PublicKey publicKey = null;
      if (certificate != null) {
        publicKey = certificate.getPublicKey();
      } else if (privateKey != null) {
        RSAPublicKeySpec spec = new RSAPublicKeySpec(privateKey.getModulus(),
            privateKey.getPublicExponent());
        publicKey = KeyFactory.getInstance(RSA_FORMAT).generatePublic(spec);
      }

      return new KeyPair(publicKey, privateKey);
    } catch (Exception exception) {
      throw new IllegalStateException("Cannot load keys from stream" , exception);
    }
  }
}
