package br.com.beertechtalents.lupulo.pocmq.rest;


import com.fasterxml.jackson.core.JsonParser;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;

@Component
public class TransferenciaAdapter {

    final RestTemplate restTemplate = new RestTemplate();

    public void call(String body) {

        HttpHeaders header = new HttpHeaders();

        header.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> resquestBody = new HttpEntity(body, header);

        JSONObject jsonObject = new JSONObject(body);
        String contaOrigem = jsonObject.getString("contaOrigem");
        String contaDestino = jsonObject.getString("contaDestino");
        BigDecimal valor = jsonObject.getBigDecimal("valor");

        String url = "http://localhost:8080/transacao/transferencia";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("contaDestino", contaDestino)
                .queryParam("contaOrigem", contaOrigem)
                .queryParam("valor", valor);


        restTemplate.postForObject(builder.toUriString(), null, String.class);

    }
}
