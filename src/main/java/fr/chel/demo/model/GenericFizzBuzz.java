package fr.chel.demo.model;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Spliterator;

public class GenericFizzBuzz implements Iterable<String> {

    private final Long limit;
    private final Integer fizzNum;
    private final Integer buzzNum;
    private final String fizzStr;
    private final String buzzStr;

    public GenericFizzBuzz(Long limit, Integer fizzNum, Integer buzzNum, String fizzStr, String buzzStr) {
        this.limit = requireNumberStrictlyPositive(limit, "limit");
        this.fizzNum = requireNumberStrictlyPositive(fizzNum, "fizzNum");
        this.buzzNum = requireNumberStrictlyPositive(buzzNum, "buzzNum");
        this.fizzStr = requireStringToBeNotBlank(fizzStr, "fizzStr");
        this.buzzStr = requireStringToBeNotBlank(buzzStr, "buzzStr");
    }

    /**
     * Helper method to check if an object is null. If the obj is null throw a {@link NullPointerException}
     * @param obj the object to check
     * @param paramaterName the name of the parameter for the exception message
     * @throws NullPointerException
     */
    private void checkNonNullParameter(Object obj, String paramaterName) {
        Objects.requireNonNull(obj, paramaterName + "is null");
    }

    /**
     * Helper method to check if a string is null or blank. If the string is null throws
     * a {@link NullPointerException}. If the string is blank throws {@link IllegalArgumentException}
     * 
     * @param string the string to check
     * @param paramaterName the name of the parameter for the exception message
     * @return the string if valid
     */
    private String requireStringToBeNotBlank(String string, String paramaterName) {
        checkNonNullParameter(string, paramaterName);
        if (string.isBlank()) {
            throw new IllegalArgumentException(paramaterName + " is blank");
        }
        return string;
    }

    /**
     * Helper method to check if a number is null or non positive. If the number is null throws
     * a {@link NullPointerException}. If the number is negative or zero throws {@link IllegalArgumentException}
     * 
     * @param number the number to check
     * @param paramaterName the name of the parameter for the exception message
     * @return the number if valid, unwrapped
     */
    private int requireNumberStrictlyPositive(Integer number, String paramaterName) {
        checkNonNullParameter(number, paramaterName);
        if (number <= 0) {
            throw new IllegalArgumentException(paramaterName + " is not strictly positive");
        }
        return number;
    }

    /**
     * Helper method to check if a number is null or non positive. If the number is null throws
     * a {@link NullPointerException}. If the number is negative or zero throws {@link IllegalArgumentException}
     * 
     * @param number the number to check
     * @param paramaterName the name of the parameter for the exception message
     * @return the number if valid, unwrapped
     */
    private long requireNumberStrictlyPositive(Long number, String paramaterName) {
        checkNonNullParameter(number, paramaterName);
        if (number <= 0) {
            throw new IllegalArgumentException(paramaterName + " is not strictly positive");
        }
        return number;
    }

    @Override
    public Spliterator<String> spliterator() {
        throw new UnsupportedOperationException();
    }

    /**
     * 
     */
    @Override
    public Iterator<String> iterator() {
        return new GenericFizzBuzzIterator(this);
    }

    private static class GenericFizzBuzzIterator implements Iterator<String> {

        private Long i;
        private GenericFizzBuzz fizzBuzz;

        private GenericFizzBuzzIterator(GenericFizzBuzz fizzBuzz) {
            this.fizzBuzz = fizzBuzz;
            this.i = 1L;
        }

        @Override
        public boolean hasNext() {
            return this.i <= this.fizzBuzz.limit;
        }

        @Override
        public String next() {
            if (this.i > this.fizzBuzz.limit) {
                throw new NoSuchElementException("limit is reached");
            }
            String result;
            if (i % (this.fizzBuzz.fizzNum * this.fizzBuzz.buzzNum) == 0) {
                result = this.fizzBuzz.fizzStr + this.fizzBuzz.buzzStr;
            } else if (i % this.fizzBuzz.fizzNum == 0) {
                result = this.fizzBuzz.fizzStr;
            } else if (i % this.fizzBuzz.buzzNum == 0) {
                result = this.fizzBuzz.buzzStr;
            } else {
                result = i.toString();
            }
            ++i;
            return result;
        }
    }
}
