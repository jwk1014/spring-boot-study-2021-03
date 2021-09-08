package com.example.demo.common;

public interface Constant {
    String EMAIL_REGEX = "^[0-9a-zA-Z]+[-.\\w]*@[0-9a-zA-Z]+[-.\\w]*(\\.[0-9a-zA-Z]+)+$";
    String PASSWORD_REGEX = "^(?=.*\\d+)(?=.*[A-Z]+)(?=.*[a-z]+)(?=.*[!@#$%\\^&*()]+)[A-Za-z\\d!@#$%\\^&*()]{8,20}$";
    String NAME_REGEX = "^[A-Za-z가-힣\\s]+$";
}
