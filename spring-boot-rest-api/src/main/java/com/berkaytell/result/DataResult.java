package com.berkaytell.result;

public class DataResult<T> extends Result implements IDataResult<T> {
    private final T data;

    public DataResult(T data, boolean success) {
        super(success);
        this.data = data;
    }

    public DataResult (T data, boolean success, String message) {
        super(success, message);
        this.data = data;
    }

    public static <T> DataResult<T> of(T data, boolean success) {
       return new DataResult<>(data, success);
    }

    public static <T> DataResult<T> of(T data, boolean success, String message) {
        return new DataResult<>(data, success, message);
    }

    @Override
    public T getData() {
        return this.data;
    }
}
