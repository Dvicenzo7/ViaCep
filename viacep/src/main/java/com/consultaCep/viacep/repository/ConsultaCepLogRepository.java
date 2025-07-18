package com.consultaCep.viacep.repository;

import com.consultaCep.viacep.model.ConsultaCepLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConsultaCepLogRepository extends JpaRepository<ConsultaCepLog, Long> {

    Optional<ConsultaCepLog> findByCep(String cep);
}
