package com.nwu.common.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyException extends RuntimeException{
    private int code;
    private String message;
}
