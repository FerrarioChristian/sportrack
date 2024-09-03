package it.unimib.icasiduso.sportrack.model;

public abstract class Result<T> {
    private Result() {
    }

    public boolean isSuccess() {
        return this instanceof Success;
    }

    public T getSuccessData() {
        if (this instanceof Success) {
            return ((Success<T>) this).data;
        } else {
            return null; // Or throw an exception
        }
    }

    public Throwable getError() {
        if (this instanceof Error) {
            return ((Error<T>) this).exception;
        } else {
            return null; // Or throw an exception
        }
    }

    public static final class Success<T> extends Result<T> {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    public static final class Error<T> extends Result<T> {
        private final String message;
        private final Throwable exception;

        public Error(String message, Throwable exception) {
            this.message = message;
            this.exception = exception;
        }

        public String getMessage() {
            return message;
        }

        public Throwable getException() {
            return exception;
        }
    }
}
