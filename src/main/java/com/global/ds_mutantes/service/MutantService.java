package com.global.ds_mutantes.service;

import com.global.ds_mutantes.entity.DnaRecord;
import com.global.ds_mutantes.exception.DnaHashCalculationException;
import com.global.ds_mutantes.repository.DnaRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MutantService {

    private final MutantDetector mutantDetector;
    private final DnaRecordRepository dnaRecordRepository;

    public boolean analyzeDna(String[] dna) {
        String hash = hashDna(dna);

        Optional<DnaRecord> existing = dnaRecordRepository.findByDnaHash(hash);
        if (existing.isPresent()) {
            return existing.get().isMutant();
        }

        boolean isMutant = mutantDetector.isMutant(dna);

        DnaRecord record = new DnaRecord();
        record.setDnaHash(hash);
        record.setMutant(isMutant);
        record.setCreatedAt(LocalDateTime.now());
        dnaRecordRepository.save(record);

        return isMutant;
    }

    private String hashDna(String[] dna) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String joined = String.join("", dna);
            byte[] encoded = digest.digest(joined.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : encoded) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new DnaHashCalculationException("Error hashing DNA", e);
        }
    }
}
