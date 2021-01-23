package chapter1.trivialcompression;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class CompressedGeneTest {

    @Test
    void testThatDecompressReturnsTheOriginalString() {
        final String gene = "TAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATATAGGGATTAACCGTTATATATATATAGCCATGGATCGATTATA";
        CompressedGene compressedGene = new CompressedGene(gene);
        final String decompressed = compressedGene.decompress();
        assertThat(decompressed).isEqualTo(gene);
    }

    @Test
    void testThatBadStringThrowsException() {
        assertThatThrownBy(() -> new CompressedGene("B"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("The provided gene String contains characters other than ACGT");
    }

    @Test
    void testThatCaseDoesNotMatter() {
        final String gene = "acgt";
        CompressedGene compressedGene = new CompressedGene(gene);
        final String decompressed = compressedGene.decompress();
        assertThat(decompressed).isEqualTo(gene.toUpperCase(Locale.ROOT));
    }
}