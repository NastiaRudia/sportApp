package com.example.myapplication.retrofit;

public class Response<T> {

    private Boolean result;
    private String message;
    private T data;

    public Boolean getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public class Builder {

        public Builder setMessage(String message) {
            Response.this.message = message;
            return this;
        }

        public Builder setResult(Boolean result) {
            Response.this.result = result;
            return this;
        }

        public Builder setData(T data) {
            Response.this.data = data;
            return this;
        }

        public Response build() {
            return Response.this;
        }
    }
}
