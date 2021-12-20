package com.kms.epicdevtools.external;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenRequest {

  @JsonProperty(value = "grant_type")
  @Builder.Default
  String grantType = "client_credentials";

  @JsonProperty(value = "client_assertion_type")
  @Builder.Default
  String clientAssertionType = "urn:ietf:params:oauth:client-assertion-type:jwt-bearer";

  @JsonProperty("client_assertion")
  String clientAssertion;
}
