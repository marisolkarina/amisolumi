package com.example.examenFinal.insfraestructure.adapter;

import com.example.examenFinal.domain.ports.out.ReniecPortOut;
import com.example.examenFinal.insfraestructure.dto.response.ReniecResponse;
import com.example.examenFinal.insfraestructure.exception.ExternalServiceException;
import com.example.examenFinal.insfraestructure.feignClient.ReniecClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ReniecAdapter implements ReniecPortOut {
    private final ReniecClient reniecClient;
    @Value("${api.token}")
    private String apiToken;

    public ReniecAdapter(ReniecClient reniecClient) {
        this.reniecClient = reniecClient;
    }

    @Override
    public ReniecResponse getPersonaInfo(String dni) {
        try {
            return reniecClient.getPersonaInfo(dni, apiToken);
        } catch (Exception ex) {
            throw new ExternalServiceException("Error al consultar Reniec", ex);
        }
    }
}
