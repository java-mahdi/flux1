package com.kh.web.application;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class CustomException extends RuntimeException {

    @NonNull
    private Integer status;
    @NonNull
    private String message;

}
