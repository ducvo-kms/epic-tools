package com.kms.epicdevtools.token;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.epicdevtools.external.AccessTokenRequest;
import com.kms.epicdevtools.external.AccessTokenResponse;
import com.kms.epicdevtools.external.EpicAuthenticationClient;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("token")
@AllArgsConstructor
public class TokenController {

  private TokenService tokenService;

  private EpicAuthenticationClient epicAuthenticationClient;

  private ObjectMapper objectMapper;


  @PostMapping()
  public TokenResponse generateTokenAccessEpic(@ModelAttribute TokenRequest request)
      throws IOException {

    logRequest(request);
    String token = tokenService.createToken(request);
    logResult(token);

    AccessTokenRequest accessTokenRequest = AccessTokenRequest.builder()
        .clientAssertion(token)
        .build();

    AccessTokenResponse accessTokenResponse = epicAuthenticationClient.getAccessToken(
        objectMapper.convertValue(accessTokenRequest, new TypeReference<Map<String, ?>>() {})
    );

    logEpicResult(accessTokenResponse);

    return new TokenResponse(accessTokenResponse.getAccessToken());
  }

  private void logRequest(TokenRequest request) {
    String line = Stream.generate(() -> "-").limit(20).collect(Collectors.joining());
    log.info("{} BEGIN-REQUEST {}", line, line);
    log.info("Client Id: {}", request.getClientId());
    log.info("Key Id: {}", request.getKeyId());
    log.info("OauthUrl: {}", request.getOauthUrl());
    log.info("Password: {}", request.getPassword());
    log.info("Key Alias: {}", request.getKeyAlias());
    log.info("File Length: {}", request.getFileJks().getSize());
    log.info("{} END_REQUEST {}", line, line);
  }

  private void logResult(String token) {
    String line = Stream.generate(() -> "-").limit(20).collect(Collectors.joining());
    log.info("{} BEGIN-RESULT {}", line, line);
    log.info("Token: {}", token);
    log.info("{} END_RESULT {}", line, line);
  }

  private void logEpicResult(AccessTokenResponse token) {
    String line = Stream.generate(() -> "-").limit(20).collect(Collectors.joining());
    log.info("{} BEGIN-RESULT {}", line, line);
    log.info("Epic Authentication: {}", token);
    log.info("{} END_RESULT {}", line, line);
  }

}
