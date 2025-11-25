package com.global.ds_mutantes.service;

import org.springframework.stereotype.Component;

@Component
public class MutantDetector {

    private static final int SEQ = 4;

    public boolean isMutant(String[] dna) {
        if (!isValidMatrix(dna)) return false;

        int n = dna.length;
        int sequences = 0;

        char[][] m = toMatrix(dna);

        // Horizontal
        for (int row = 0; row < n; row++) {
            for (int col = 0; col <= n - SEQ; col++) {
                if (checkHorizontal(m, row, col)) {
                    sequences++;
                    if (sequences > 1) return true;
                }
            }
        }

        // Vertical
        for (int col = 0; col < n; col++) {
            for (int row = 0; row <= n - SEQ; row++) {
                if (checkVertical(m, row, col)) {
                    sequences++;
                    if (sequences > 1) return true;
                }
            }
        }

        // Diagonal ↘
        for (int row = 0; row <= n - SEQ; row++) {
            for (int col = 0; col <= n - SEQ; col++) {
                if (checkDiagonal(m, row, col)) {
                    sequences++;
                    if (sequences > 1) return true;
                }
            }
        }

        // Diagonal ↗
        for (int row = SEQ - 1; row < n; row++) {
            for (int col = 0; col <= n - SEQ; col++) {
                if (checkDiagonalInv(m, row, col)) {
                    sequences++;
                    if (sequences > 1) return true;
                }
            }
        }

        return false;
    }

    private boolean isValidMatrix(String[] dna) {
        if (dna == null || dna.length == 0) return false;
        int n = dna.length;

        for (String s : dna) {
            if (s == null || s.length() != n) return false;
            if (!s.matches("[ATCG]+")) return false;
        }
        return true;
    }

    private char[][] toMatrix(String[] dna) {
        int n = dna.length;
        char[][] m = new char[n][n];
        for (int i = 0; i < n; i++) {
            m[i] = dna[i].toCharArray();
        }
        return m;
    }

    private boolean checkHorizontal(char[][] m, int r, int c) {
        char base = m[r][c];
        return m[r][c+1] == base &&
                m[r][c+2] == base &&
                m[r][c+3] == base;
    }

    private boolean checkVertical(char[][] m, int r, int c) {
        char base = m[r][c];
        return m[r+1][c] == base &&
                m[r+2][c] == base &&
                m[r+3][c] == base;
    }

    private boolean checkDiagonal(char[][] m, int r, int c) {
        char base = m[r][c];
        return m[r+1][c+1] == base &&
                m[r+2][c+2] == base &&
                m[r+3][c+3] == base;
    }

    private boolean checkDiagonalInv(char[][] m, int r, int c) {
        char base = m[r][c];
        return m[r-1][c+1] == base &&
                m[r-2][c+2] == base &&
                m[r-3][c+3] == base;
    }
}
