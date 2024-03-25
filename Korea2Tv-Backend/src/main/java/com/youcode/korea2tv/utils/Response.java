package com.youcode.korea2tv.utils;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
@Setter
@Builder
public class Response<T>{
    private String message;
    private T result;
    private Map<String, String> errorsValidation;
    private String error;
}
