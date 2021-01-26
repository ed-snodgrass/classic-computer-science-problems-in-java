package chapter2.search.dna;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class GeneTest {

    @Test
    void linearContainsAcg() {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATATCCCTAGGACTCCCTTT";
        Gene myGene = new Gene(geneStr);
        Gene.Codon acg = new Gene.Codon("ACG");
        assertThat(myGene.linearContains(acg)).isTrue();
    }
    @Test
    void linearContainsGat() {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATATCCCTAGGACTCCCTTT";
        Gene myGene = new Gene(geneStr);
        Gene.Codon gat = new Gene.Codon("GAT");
        assertThat(myGene.linearContains(gat)).isFalse();
    }

    @Test
    void binaryContainsAcg() {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATATCCCTAGGACTCCCTTT";
        Gene myGene = new Gene(geneStr);
        Gene.Codon acg = new Gene.Codon("ACG");
        assertThat(myGene.binaryContains(acg)).isTrue();
    }

    @Test
    void binaryContainsGat() {
        String geneStr = "ACGTGGCTCTCTAACGTACGTACGTACGGGGTTTATATATATCCCTAGGACTCCCTTT";
        Gene myGene = new Gene(geneStr);
        Gene.Codon gat = new Gene.Codon("GAT");
        assertThat(myGene.binaryContains(gat)).isFalse();
    }
}