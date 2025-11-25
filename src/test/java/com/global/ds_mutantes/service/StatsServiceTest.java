package com.global.ds_mutantes.service;

import com.global.ds_mutantes.dto.StatsResponse;
import com.global.ds_mutantes.repository.DnaRecordRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StatsServiceTest {

    @Mock
    private DnaRecordRepository repository;

    @InjectMocks
    private StatsService service;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    // 1
    @Test
    @DisplayName("Estadísticas correctas con datos")
    void testStats() {
        when(repository.countByIsMutant(true)).thenReturn(40L);
        when(repository.countByIsMutant(false)).thenReturn(100L);

        StatsResponse r = service.getStats();
        assertEquals(40, r.getCountMutantDna());
        assertEquals(100, r.getCountHumanDna());
        assertEquals(0.4, r.getRatio(), 0.001);
    }

    // 2
    @Test
    @DisplayName("Ratio 0 cuando no hay humanos")
    void testNoHumans() {
        when(repository.countByIsMutant(true)).thenReturn(10L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse r = service.getStats();
        assertEquals(10, r.getCountMutantDna());
        assertEquals(0, r.getCountHumanDna());
        assertEquals(0.0, r.getRatio(), 0.001);
    }

    // 3
    @Test
    @DisplayName("Sin datos devuelve 0 en todo")
    void testNoData() {
        when(repository.countByIsMutant(true)).thenReturn(0L);
        when(repository.countByIsMutant(false)).thenReturn(0L);

        StatsResponse r = service.getStats();
        assertEquals(0, r.getCountMutantDna());
        assertEquals(0, r.getCountHumanDna());
        assertEquals(0.0, r.getRatio(), 0.001);
    }

    // 4
    @Test
    @DisplayName("Ratio decimal correcto")
    void testRatioDecimal() {
        when(repository.countByIsMutant(true)).thenReturn(1L);
        when(repository.countByIsMutant(false)).thenReturn(3L);

        StatsResponse r = service.getStats();
        assertEquals(0.333, r.getRatio(), 0.01);
    }

    // 5
    @Test
    @DisplayName("Ratio 1 cuando mutantes = humanos")
    void testEqualCounts() {
        when(repository.countByIsMutant(true)).thenReturn(50L);
        when(repository.countByIsMutant(false)).thenReturn(50L);

        StatsResponse r = service.getStats();
        assertEquals(1.0, r.getRatio(), 0.0001);
    }

    // 6
    @Test
    @DisplayName("Maneja números grandes sin overflow")
    void testLargeNumbers() {
        when(repository.countByIsMutant(true)).thenReturn(1_000_000L);
        when(repository.countByIsMutant(false)).thenReturn(2_000_000L);

        StatsResponse r = service.getStats();
        assertEquals(0.5, r.getRatio(), 0.001);
    }
}
