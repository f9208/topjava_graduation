package ru.topjava.graduation.repository;

import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;


public class TestMatcher<T> {
    private final BiConsumer<T, T> assertion;
    private final BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion;

    public TestMatcher(BiConsumer<T, T> assertion, BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion) {
        this.assertion = assertion;
        this.iterableAssertion = iterableAssertion;
    }

    public static <T> TestMatcher<T> usingIgnoreFieldsComparator(String... ignoringFields) {
        return new TestMatcher<>((a, e) -> assertThat(a).usingRecursiveComparison()
                .ignoringFields(ignoringFields).isEqualTo(e),
                (a, e) -> assertThat(a).usingRecursiveComparison().ignoringCollectionOrder()
                        .ignoringFields(ignoringFields).isEqualTo(e));
    }

    public void assertMatch(T actual, T expected) {
        assertion.accept(actual, expected);
    }

    public void assertMatch(Iterable<T> actual, T... expected) {
        iterableAssertion.accept(actual, List.of(expected));
    }
}
