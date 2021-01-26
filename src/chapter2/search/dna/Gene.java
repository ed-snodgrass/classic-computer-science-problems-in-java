package chapter2.search.dna;

import chapter2.search.GenericSearch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class Gene {
    public enum Nucleotide {
        A, C, G, T
    }
    public static class Codon implements Comparable<Codon> {
        public final Nucleotide first, second, third;
        private final Comparator<Codon> comparator = Comparator.comparing((Codon c) -> c.first).thenComparing((Codon c) -> c.second).thenComparing((Codon c) -> c.third);

        public Codon(String codonStr) {
            first = Nucleotide.valueOf(codonStr.substring(0,1));
            second = Nucleotide.valueOf(codonStr.substring(1,2));
            third = Nucleotide.valueOf(codonStr.substring(2,3));
        }

        @Override
        public int compareTo(Codon other) {
            return comparator.compare(this, other);
        }
    }

    private ArrayList<Codon> codons = new ArrayList<>();

    public Gene(String geneStr) {
        for (int i = 0; i < geneStr.length() - 3; i += 3) {
            codons.add(new Codon(geneStr.substring(i, i + 3)));
        }
    }

    public boolean linearContains(Codon key) {
        return GenericSearch.linearContains(codons, key);
    }

    public boolean binaryContains(Codon key) {
        /*
        In situations where you're only searching once it is likely better to perform linear search.
         */
        ArrayList<Codon> sortedCodons = new ArrayList<>(codons);
        Collections.sort(sortedCodons);
        return GenericSearch.binaryContains(sortedCodons, key);
    }
}
