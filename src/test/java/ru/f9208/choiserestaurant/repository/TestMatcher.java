package ru.f9208.choiserestaurant.repository;

import org.springframework.test.web.servlet.ResultMatcher;
import ru.f9208.choiserestaurant.TestUtil;

import java.util.List;
import java.util.function.BiConsumer;

import static org.assertj.core.api.Assertions.assertThat;


public class TestMatcher<T> {
    private final BiConsumer<T, T> assertion;
    private final BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion;
    private final Class<T> clazz;

    public TestMatcher(BiConsumer<T, T> assertion, BiConsumer<Iterable<T>, Iterable<T>> iterableAssertion, Class<T> clazz) {
        this.assertion = assertion;
        this.iterableAssertion = iterableAssertion;
        this.clazz = clazz;
    }

    public static <T> TestMatcher<T> usingIgnoreFieldsComparator(Class<T> clazz, String... ignoringFields) {
        return new TestMatcher<>((a, e) -> assertThat(a).usingRecursiveComparison()
                .ignoringFields(ignoringFields).isEqualTo(e),
                (a, e) -> assertThat(a).usingRecursiveComparison().ignoringCollectionOrder()
                        .ignoringFields(ignoringFields).isEqualTo(e), clazz);
    }

    public static <T> TestMatcher<T> usingEqualsComparator(Class<T> clazz) {
        return new TestMatcher<>((a, e) -> assertThat(a).isEqualTo(e),
                (a, e) -> assertThat(a).isEqualTo(e), clazz);
    }

    public void assertMatch(T actual, T expected) {
        assertion.accept(actual, expected);
    }

    public void assertMatch(Iterable<T> actual, T... expected) {
        iterableAssertion.accept(actual, List.of(expected));
    }

    public void assertMatch(Iterable<T> actual, Iterable<T> expected) {
        iterableAssertion.accept(actual, expected);
    }

    public ResultMatcher contentJson(T expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, clazz), expected);
    }

    public ResultMatcher contentJson(T... expected) {
        return contentJson(List.of(expected));
    }

    public ResultMatcher contentJson(List<T> expected) {
        return result -> assertMatch(TestUtil.readListFromJsonMvcResult(result, clazz), expected);
    }
}
