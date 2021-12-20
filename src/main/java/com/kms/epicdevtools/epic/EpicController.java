package com.kms.epicdevtools.epic;

import ca.uhn.fhir.context.FhirContext;
import ca.uhn.fhir.model.api.IQueryParameterType;
import ca.uhn.fhir.model.dstu2.resource.Contract.Actor;
import ca.uhn.fhir.parser.IParser;
import ca.uhn.fhir.rest.client.api.IGenericClient;
import ca.uhn.fhir.rest.client.interceptor.BearerTokenAuthInterceptor;
import ca.uhn.fhir.rest.client.interceptor.LoggingInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.ContentType;
import org.hl7.fhir.dstu3.model.Appointment;
import org.hl7.fhir.dstu3.model.Bundle;
import org.hl7.fhir.dstu3.model.Practitioner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/epic")
public class EpicController {

  @GetMapping("/test")
  public ResponseEntity<String> test(@RequestParam("token") String token, @RequestParam("server") String server) {
    FhirContext ctx = FhirContext.forDstu3();
    log.info("server: {}", server);
    IGenericClient client = ctx.newRestfulGenericClient(server);

    BearerTokenAuthInterceptor authInterceptor = new BearerTokenAuthInterceptor(token);

    client.registerInterceptor(authInterceptor);
    client.registerInterceptor(new LoggingInterceptor());

     Bundle pt = client.search()
        .forResource(Appointment.class)
        .where(Appointment.PATIENT.hasId("erXuFYUfucBZaryVksYEcMg3"))
         .count(10)
        .returnBundle(Bundle.class)
        .execute();


    IParser parser = ctx.newJsonParser();

    String serialized = parser.encodeResourceToString(pt);

    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
        .body(serialized);
  }
}
