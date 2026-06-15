package com.example.examenFinal.domain.ports.out;

import com.example.examenFinal.insfraestructure.dto.response.ReniecResponse;

public interface ReniecPortOut {
    ReniecResponse getPersonaInfo(String dni);
}
