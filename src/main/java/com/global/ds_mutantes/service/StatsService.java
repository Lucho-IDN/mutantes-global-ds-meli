package com.global.ds_mutantes.service;

import com.global.ds_mutantes.dto.StatsResponse;
import com.global.ds_mutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatsService {

    private final DnaRecordRepository dnaRecordRepository;

    public StatsResponse getStats() {
        long mutants = dnaRecordRepository.countByIsMutant(true);
        long humans = dnaRecordRepository.countByIsMutant(false);
        double ratio = humans == 0 ? 0 : (double) mutants / humans;
        return new StatsResponse(mutants, humans, ratio);
    }
}
