package com.consultaCep.viacep.controller;

import com.consultaCep.viacep.dto.CepResponse;
import com.consultaCep.viacep.service.ViaCepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class ViaCepController {

    @Autowired private ViaCepService viaCepService;


    @GetMapping("/cep/{cep}")
    public CepResponse getEnderecoByCep(@PathVariable("cep") String cep){
        return viaCepService.consultaCep(cep);
    }
}
