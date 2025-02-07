package br.com.ordermanager.infra.utils;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Try<T> {

    public static <T> T failure(RuntimeException exceptionSupplier) {
        throw new RuntimeException(exceptionSupplier.getMessage());
    }

    public abstract T get() throws Throwable;

    public abstract boolean isSuccess();

    public abstract boolean isFailure();

    public abstract Throwable getCause();

    public static <T> Try<T> of(Supplier<T> supplier) {
        try {
            return new Success<>(supplier.get());
        } catch (Throwable t) {
            return new Failure<>(t);
        }
    }

    public <U> Try<U> map(Function<? super T, ? extends U> mapper) {
        if (isSuccess()) {
            try {
                return new Success<>(mapper.apply(get()));
            } catch (Throwable t) {
                return new Failure<>(t);
            }
        } else {
            return new Failure<>(getCause());
        }
    }

    public <U> Try<U> flatMap(Function<? super T, Try<U>> mapper) {
        if (isSuccess()) {
            try {
                return mapper.apply(get());
            } catch (Throwable t) {
                return new Failure<>(t);
            }
        } else {
            return new Failure<>(getCause());
        }
    }

    public T getOrElse(T other) {
        if (isSuccess()) {
            try {
                return get();
            } catch (Throwable t) {
                // This should not happen, but just in case
                return other;
            }
        } else {
            return other;
        }
    }

    public T getOrElseThrow(Function<Throwable, ? extends RuntimeException> exceptionSupplier) {
        if (isSuccess()) {
            try {
                return get();
            } catch (Throwable t) {
                throw exceptionSupplier.apply(t);
            }
        } else {
            throw exceptionSupplier.apply(getCause());
        }
    }

    public static final class Success<T> extends Try<T> {
        private final T value;

        private Success(T value) {
            this.value = value;
        }

        @Override
        public T get() {
            return value;
        }

        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isFailure() {
            return false;
        }

        @Override
        public Throwable getCause() {
            throw new UnsupportedOperationException("Success does not have a cause");
        }
    }

    public static final class Failure<T> extends Try<T> {
        private final Throwable cause;

        private Failure(Throwable cause) {
            this.cause = cause;
        }

        @Override
        public T get() throws Throwable {
            throw cause;
        }

        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isFailure() {
            return true;
        }

        @Override
        public Throwable getCause() {
            return cause;
        }
    }
}