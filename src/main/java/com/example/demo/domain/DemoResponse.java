package com.example.demo.domain;

public class DemoResponse {
    private String text;

    public DemoResponse() {
    }

    public DemoResponse(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
