package chapter2.search;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GenericSearchTest {

    @Test
    void linearContainsReturnsTrueWhenFound() {
        List<Integer> integerList = List.of(1, 5, 15, 15, 15, 15, 20);
        assertThat(GenericSearch.linearContains(integerList, 5)).isTrue();
    }

    @Test
    void linearContainsReturnsFalseWhenNotFound() {
        List<Integer> integerList = List.of(1, 5, 15, 15, 15, 15, 20);
        assertThat(GenericSearch.linearContains(integerList, 7)).isFalse();
    }

    @Test
    void binaryContainsReturnsTrueWhenFound() {
        List<Integer> integerList = List.of(1, 5, 15, 15, 15, 15, 20);
        assertThat(GenericSearch.binaryContains(integerList, 5)).isTrue();
    }

    @Test
    void binaryContainsReturnsFalseWhenNotFound() {
        List<Integer> integerList = List.of(1, 5, 15, 15, 15, 15, 20);
        assertThat(GenericSearch.binaryContains(integerList, 7)).isFalse();
    }
}