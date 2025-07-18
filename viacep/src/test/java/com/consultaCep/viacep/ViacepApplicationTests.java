package com.consultaCep.viacep;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 8081)
class ViacepApplicationTests {

	@Autowired
	private MockMvc mockMvc;



	@Test
	void testBuscaCepComWireMock() throws Exception {

		//Um json fixo, que simula o retorno da Api para o cep enviado
		String jsonResponse = "{"
				+ "\"cep\":\"08040-275\","
				+ "\"logradouro\":\"Rua Sararaca\","
				+ "\"bairro\":\"Cidade São Miguel\","
				+ "\"localidade\":\"São Paulo\","
				+ "\"uf\":\"SP\""
				+ "}";

		//Configura para consumir a api
		WireMock.stubFor(WireMock.get("/cep/08040275")
				.willReturn(WireMock.aResponse()
						.withHeader("Content-Type", "application/json")
						.withBody(jsonResponse))); //A resposta simulada da API, é o json que definimos acima

		//Bate no controller, no endpoint que criamos
		mockMvc.perform(get("/cep/08040275"))
				.andExpect(status().isOk()) // Valida se o status é 200
				.andExpect(content().json(jsonResponse));// Valida se o json retornado é igual ao que definimos acima
	}

}
