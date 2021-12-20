package com.kms.epicdevtools.external;

import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
    name = "epic",
    url = "https://fhir.epic.com/interconnect-fhir-oauth/oauth2/token",
    configuration = UrlEncodedConfiguration.class
)
public interface EpicAuthenticationClient {

  @PostMapping(consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
  AccessTokenResponse getAccessToken(Map<String, ?> request);
}
