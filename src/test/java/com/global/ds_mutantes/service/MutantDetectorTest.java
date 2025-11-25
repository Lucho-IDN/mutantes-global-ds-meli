package com.global.ds_mutantes.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MutantDetectorTest {

    private MutantDetector detector;

    @BeforeEach
    void setUp() {
        detector = new MutantDetector();
    }

    // 1
    @Test
    @DisplayName("Detecta mutante con secuencia horizontal")
    void testHorizontalSequence() {
        String[] dna = {
                "AAAAGC",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 2
    @Test
    @DisplayName("Detecta mutante con secuencia vertical")
    void testVerticalSequence() {
        String[] dna = {
                "ATGCGA",
                "ATGTGC",
                "ATATGT",
                "ATAAGG",
                "ACCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 3
    @Test
    @DisplayName("Detecta mutante con diagonal principal ↘")
    void testDiagonalSequence() {
        String[] dna = {
                "ATGCAA",
                "CAGTAC",
                "TTATAT",
                "AGAAAG",
                "CCCATA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 4
    @Test
    @DisplayName("Detecta mutante con diagonal inversa ↗")
    void testReverseDiagonalSequence() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTACTT",
                "AGAAAG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 5
    @Test
    @DisplayName("Mutante con múltiples secuencias horizontales")
    void testMultipleHorizontal() {
        String[] dna = {
                "AAAAAA",
                "CCCCCC",
                "TTATGT",
                "AGAAGG",
                "CACCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 6
    @Test
    @DisplayName("Mutante con secuencia horizontal y diagonal")
    void testHorizontalAndDiagonal() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 7
    @Test
    @DisplayName("NO mutante con ninguna secuencia")
    void testNoSequences() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    // 8
    @Test
    @DisplayName("NO mutante sin ninguna secuencia (caso humano válido)")
    void testHumanNoSequencesSafe() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "GTATGT",
                "AGACGG",
                "GCGTCA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    // 9
    @Test
    @DisplayName("Valida matriz nula")
    void testNullMatrix() {
        assertFalse(detector.isMutant(null));
    }

    // 10
    @Test
    @DisplayName("Valida matriz vacía")
    void testEmptyMatrix() {
        assertFalse(detector.isMutant(new String[]{}));
    }

    // 11
    @Test
    @DisplayName("Valida matriz no cuadrada")
    void testNonSquareMatrix() {
        String[] dna = {
                "ATGCGA",
                "CAGTGC",
                "TTATGT"    // 3x6 matriz
        };
        assertFalse(detector.isMutant(dna));
    }

    // 12
    @Test
    @DisplayName("Valida caracteres inválidos")
    void testInvalidCharacters() {
        String[] dna = {
                "ATGCGA",
                "CAGTXC",  // X inválido
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }

    // 13
    @Test
    @DisplayName("Detecta mutante en matriz mínima 4x4")
    void testSmallMatrix() {
        String[] dna = {
                "AAAA",
                "CCCC",
                "TTAT",
                "AGAC"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 14
    @Test
    @DisplayName("Matriz grande 10x10 con secuencias múltiples")
    void testLargeMatrix() {
        String[] dna = {
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA",
                "CCCCTACCCC",
                "TCACTGTCAC",
                "ATGCGAATGC",
                "CAGTGCCAGT",
                "TTATGTTTAT",
                "AGAAGGATAA"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 15
    @Test
    @DisplayName("Detecta early termination al encontrar dos secuencias rápido")
    void testEarlyTermination() {
        String[] dna = {
                "AAAAGA", // secuencia 1
                "AAAAGC", // secuencia 2
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertTrue(detector.isMutant(dna));
    }

    // 16
    @Test
    @DisplayName("Fila nula en el arreglo")
    void testNullRow() {
        String[] dna = {
                "ATGCGA",
                null,
                "TTATGT",
                "AGAAGG",
                "CCCCTA",
                "TCACTG"
        };
        assertFalse(detector.isMutant(dna));
    }
}
