package com.projet.project_e_banking.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicateFieldException extends RuntimeException{
    public DuplicateFieldException(String message) {
        super(message);
    }
}
