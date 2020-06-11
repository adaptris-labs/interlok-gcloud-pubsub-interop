package com.adaptris.labs;

import com.palantir.docker.compose.DockerComposeExtension;
import com.palantir.docker.compose.connection.Container;
import com.palantir.docker.compose.connection.DockerPort;
import com.palantir.docker.compose.connection.waiting.HealthChecks;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GcloudPubsubInteropIT {

  @RegisterExtension
  public static DockerComposeExtension docker = DockerComposeExtension.builder()
    .file("docker-compose.yaml")
    .waitingForService("interlok", HealthChecks.toRespond2xxOverHttp(8080, (port) -> port.inFormat("http://$HOST:$EXTERNAL_PORT/workflow-health-check/ready")))
    .build();

  @Test
  public void apiGCP() throws IOException {
    DockerPort interlokDockerPort = docker.containers().container("interlok").port(8080);
    try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
      HttpGet request = new HttpGet(interlokDockerPort.inFormat("http://$HOST:$EXTERNAL_PORT/api/gcp"));
      try (CloseableHttpResponse response = httpClient.execute(request)){
        assertEquals(200, response.getStatusLine().getStatusCode());
      }
    }
  }
}
