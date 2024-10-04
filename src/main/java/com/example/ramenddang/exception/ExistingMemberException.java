package com.example.ramenddang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExistingMemberException extends RuntimeException {
    public ExistingMemberException() {
        super("이미 존재하는 회원입니다.");
    }
}
