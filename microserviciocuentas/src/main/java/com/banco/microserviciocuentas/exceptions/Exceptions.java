/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.banco.microserviciocuentas.exceptions;

/**
 *
 * @author Nancy Mejia
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

/**
 * Exceptions.
 */
@Getter
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed", "status", "errorEnum"})

public class Exceptions extends RuntimeException {
    private final int status;
    private final String code;
    private final ErrorEnum errorEnum;

    public Exceptions(final ErrorEnum errorEnum) {
        this(errorEnum, (Throwable)null);
    }

    public Exceptions(final ErrorEnum errorEnum, final Throwable cause) {
        this(errorEnum, 500, cause);
    }

    public Exceptions(final ErrorEnum errorEnum, final int httpStatus) {
        this(errorEnum, httpStatus, (Throwable)null);
    }

    public Exceptions(final ErrorEnum errorEnum, final int httpStatus, final Throwable cause) {
        super(errorEnum.getMessage(), cause);
        this.errorEnum = errorEnum;
        this.status = httpStatus;
        this.code = errorEnum.getCode();
    }
}

