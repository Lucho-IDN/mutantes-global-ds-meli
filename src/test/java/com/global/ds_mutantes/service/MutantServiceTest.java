package com.global.ds_mutantes.service;

import com.global.ds_mutantes.entity.DnaRecord;
import com.global.ds_mutantes.exception.DnaHashCalculationException;
import com.global.ds_mutantes.repository.DnaRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MutantServiceTest {

    @Mock
    private MutantDetector detector;

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private MutantService service;

    private String[] mutantDna;
    private String[] humanDna;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        mutantDna = new String[]{"AAAA", "CCCC", "TTTT", "GGGG"};
        humanDna = new String[]{"ATGC", "CAGT", "TTAT", "AGAC"};
    }

    // 1
    @Test
    @DisplayName("Devuelve resultado cacheado si el ADN ya existe")
    void testCachedResult() {
        DnaRecord record = new DnaRecord(1L, "hash", true, LocalDateTime.now());
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.of(record));

        boolean result = service.analyzeDna(mutantDna);

        assertTrue(result);
        verify(detector, never()).isMutant(any());
        verify(repository, never()).save(any());
    }

    // 2
    @Test
    @DisplayName("Guarda ADN mutante y retorna true")
    void testSaveMutant() {
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(mutantDna)).thenReturn(true);

        boolean result = service.analyzeDna(mutantDna);

        assertTrue(result);
        verify(repository).save(any(DnaRecord.class));
        verify(detector).isMutant(mutantDna);
    }

    // 3
    @Test
    @DisplayName("Guarda ADN humano y retorna false")
    void testSaveHuman() {
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(humanDna)).thenReturn(false);

        boolean result = service.analyzeDna(humanDna);

        assertFalse(result);
        verify(repository).save(any(DnaRecord.class));
    }

    // 4
    @Test
    @DisplayName("Hash consistente y de longitud SHA-256 (64 chars)")
    void testHashConsistency() {
        when(repository.findByDnaHash(anyString())).thenReturn(Optional.empty());
        when(detector.isMutant(mutantDna)).thenReturn(true);

        service.analyzeDna(mutantDna);
        service.analyzeDna(mutantDna);

        verify(repository, times(2)).findByDnaHash(anyString());
    }

    // 5
    @Test
    @DisplayName("Lanza excepción si falla el hashing")
    void testHashException() {
        MutantService faulty = spy(service);

        doThrow(new DnaHashCalculationException("fail", new RuntimeException()))
                .when(faulty).analyzeDna(any());

        assertThrows(DnaHashCalculationException.class,
                () -> faulty.analyzeDna(mutantDna));
    }
}
