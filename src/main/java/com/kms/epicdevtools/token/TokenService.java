package com.kms.epicdevtools.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kms.epicdevtools.utils.JksExtractor;
import java.io.IOException;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  public String createToken(TokenRequest request) throws IOException {

    KeyPair keyPair = JksExtractor.execute(request.getPassword(), request.getKeyAlias(),
        request.getFileJks().getInputStream());

    Algorithm algorithm = Algorithm.RSA384((RSAPublicKey) keyPair.getPublic(),
        (RSAPrivateKey) keyPair.getPrivate());

    return JWT.create()
        .withSubject(request.getClientId())
        .withIssuer(request.getClientId())
        .withJWTId(UUID.randomUUID().toString())
        .withAudience(request.getOauthUrl())
        .withIssuedAt(new Date(System.currentTimeMillis()))
        .withExpiresAt(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
        .sign(algorithm);
  }
}
