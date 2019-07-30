package com.company;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Scanner;

public class SecondDecision {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(listOfPaths(scanner.next(), scanner.next()));
    }

    public static String listOfPaths(String src, String dest) {
        if (src.length() != dest.length()) {
            throw new IllegalArgumentException("The digits have a different length.");
        }
        final StringBuilder lisOfPaths = new StringBuilder();
        final char[] srcV = src.toCharArray();
        final char[] destV = dest.toCharArray();
        for (int i = srcV.length - 1; i >= 0; i--) {
            final char[] v = Arrays.copyOf(srcV, srcV.length);
            v[i] = v[i] == '0' ? '1' : '0';
            lisOfPaths.append(srcV).append(" ").append(v).append(" ");
            final Deque<Integer> q = differentBits(v, destV);
            for (int j = 0; j < q.size(); j++) {
                final int k = q.element();
                if (i <= k) {
                    q.add(q.remove());
                }
            }
            while (!q.isEmpty()) {
                final int k = q.remove();
                v[k] = v[k] == '0' ? '1' : '0';
                lisOfPaths.append(v).append(" ");
            }
            lisOfPaths.append("\n");
        }
        return lisOfPaths.toString();
    }

    private static Deque<Integer> differentBits(char[] b1, char[] b2) {
        Deque<Integer> listOfSingleBits = new ArrayDeque<>();
        for (int i = b1.length - 1; i >= 0; i--) {
            if (b1[i] != b2[i]) {
                listOfSingleBits.add(i);
            }
        }
        return listOfSingleBits;
    }
}
