package com.global.ds_mutantes.dto;

import com.global.ds_mutantes.validation.ValidDnaSequence;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DnaRequest {

    @NotNull
    @ValidDnaSequence
    private String[] dna;
}
