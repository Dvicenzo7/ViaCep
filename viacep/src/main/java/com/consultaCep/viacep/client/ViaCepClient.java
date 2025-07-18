package com.consultaCep.viacep.client;

import com.consultaCep.viacep.dto.CepResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ViaCepClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public CepResponse buscarCep(String cep) {
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        return restTemplate.getForObject(url, CepResponse.class);
    }
}
