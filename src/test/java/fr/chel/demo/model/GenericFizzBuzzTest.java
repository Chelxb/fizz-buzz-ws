package fr.chel.demo.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
import java.util.stream.Stream;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GenericFizzBuzzTest {

    private static Stream<Arguments> invalidConstructorParameters() {
        return Stream.of(
                Arguments.of(-1L, 2, 3, "Fizz", "Buzz"),
                Arguments.of(20L, -2, 3, "Fizz", "Buzz"),
                Arguments.of(20L, 2, -3, "Fizz", "Buzz"),
                Arguments.of(20L, 2, 3, "", "Buzz"),
                Arguments.of(20L, 2, 3, "Fizz", ""));
    }

    @ParameterizedTest
    @MethodSource("invalidConstructorParameters")
    void constructor_throwsWhenConstructorParametersAreInvalid(Long limit, Integer fizzNum, Integer buzzNum,
            String fizzStr, String buzzStr) {
        // Given : method parameters

        ThrowingCallable when = () -> new GenericFizzBuzz(limit, fizzNum, buzzNum, fizzStr, buzzStr);

        assertThatThrownBy(when)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageMatching("\\w+ is (not strictly positive|blank)");
    }

    @Test
    void iterator_hasNext_returnFalsePassedLimit() {
        GenericFizzBuzz fizzBuzz = new GenericFizzBuzz(5L, 1, 2, "Fizz", "Buzz");
        Iterator<String> fizzBuzzIterator = fizzBuzz.iterator();

        for (int i = 0; i < 5; ++i) {
            fizzBuzzIterator.next();
        }

        assertThat(fizzBuzzIterator.hasNext())
                .isFalse();
    }

    @Test
    void iterator_next_throwsPassedLimit() {
        GenericFizzBuzz fizzBuzz = new GenericFizzBuzz(5L, 1, 2, "Fizz", "Buzz");
        Iterator<String> fizzBuzzIterator = fizzBuzz.iterator();

        for (int i = 0; i < 5; ++i) {
            fizzBuzzIterator.next();
        }
        ThrowingCallable when = () -> fizzBuzzIterator.next();

        assertThatThrownBy(when)
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void iterator_next_returnsTheRightSequence() {
        GenericFizzBuzz fizzBuzz = new GenericFizzBuzz(15L, 3, 5, "Fizz", "Buzz");
        StringJoiner resultLines = new StringJoiner("\n");

        fizzBuzz.forEach(resultLines::add);

        Assertions.assertThat(resultLines)
            .hasToString(("""
                    1
                    2
                    Fizz
                    4
                    Buzz
                    Fizz
                    7
                    8
                    Fizz
                    Buzz
                    11
                    Fizz
                    13
                    14
                    FizzBuzz"""));
    }
}
