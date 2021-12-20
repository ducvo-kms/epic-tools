package com.kms.epicdevtools.token;

import java.io.IOException;
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


  @PostMapping()
  public TokenResponse generateTokenAccessEpic(@ModelAttribute TokenRequest request)
      throws IOException {

    logRequest(request);
    String token = tokenService.createToken(request);
    logResult(token);

    return new TokenResponse(token);
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

}
