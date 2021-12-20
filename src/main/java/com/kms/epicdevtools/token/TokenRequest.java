package com.kms.epicdevtools.token;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TokenRequest {
  String clientId;
  String oauthUrl;
  String password;
  String keyAlias;
  MultipartFile fileJks;
}
