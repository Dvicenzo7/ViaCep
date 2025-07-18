package com.consultaCep.viacep.service;

import com.consultaCep.viacep.client.ViaCepClient;
import com.consultaCep.viacep.dto.CepResponse;
import com.consultaCep.viacep.model.ConsultaCepLog;
import com.consultaCep.viacep.repository.ConsultaCepLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ViaCepService {

    @Autowired private  ViaCepClient viaCepClient;
    @Autowired private ConsultaCepLogRepository consultaCepLogRepository;


    public CepResponse consultaCep(String cep) {

        //Validação para conter apenas 8 digitos
        if (!cep.matches("\\d{8}")) {
            throw new IllegalArgumentException("CEP inválido. Deve conter exatamente 8 dígitos e sem traços.");
        }

        //Verifica se existe o cep no banco de dados
        Optional<ConsultaCepLog> registroExistente = consultaCepLogRepository.findByCep(cep);

        if (registroExistente.isPresent()) {
            ConsultaCepLog log = registroExistente.get();

            CepResponse response = new CepResponse();
            response.setCep(log.getCep());
            response.setLogradouro(log.getLogradouro());
            response.setBairro(log.getBairro());
            response.setLocalidade(log.getLocalidade());
            response.setUf(log.getUf());

            return response;
        }

        try {

            //Não tem o cep no banco de dados, vamos buscar na api do Correio
            CepResponse response = viaCepClient.buscarCep(cep);

            if (response.getCep() == null) {
                throw new RuntimeException("CEP não encontrado na base dos Correios.");
            }

            ConsultaCepLog log = new ConsultaCepLog();
            log.setCep(response.getCep());
            log.setLogradouro(response.getLogradouro());
            log.setBairro(response.getBairro());
            log.setLocalidade(response.getLocalidade());
            log.setUf(response.getUf());
            log.setDataHoraConsulta(LocalDateTime.now());

            consultaCepLogRepository.save(log);

            return response;
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("CEP não encontrado na base dos Correios.");
        } catch (RestClientException e) {
            throw new RuntimeException("Erro ao consultar a API dos Correios (ViaCEP): " + e.getMessage());
        }
    }
}
