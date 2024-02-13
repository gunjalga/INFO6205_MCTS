package edu.neu.coe.info6205.union_find;

import java.util.Random;

public class UF_client {
    public static int count(int n) {
        UF_HWQUPC uf = new UF_HWQUPC(n);
        Random rand = new Random();
        int connections = 0;

        while (uf.components() > 1) {
            int p = rand.nextInt(n);
            int q = rand.nextInt(n);
            if (!uf.connected(p, q)) {
                uf.union(p, q);
                connections++;
            }
        }

        return connections;
    }

    public static void main(String[] args) {


        int[] sizes= {1000,2000,5000,100000};
        for (int size : sizes) {
            System.out.println("Number of sites: " + size);
            int numConnections = count(size);
            System.out.println("Number of connections generated: " + numConnections);
        }

    }
}
