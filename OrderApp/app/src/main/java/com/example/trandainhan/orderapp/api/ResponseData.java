package com.example.trandainhan.orderapp.api;


public class ResponseData<T> {
    public int status;
    public T data;
    public String message;

    public ResponseData(int status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public ResponseData(int status, T data) {
        this.status = status;
        this.data = data;
    }
}
