package com.yash.userservice.exception;

import lombok.*;
import org.springframework.http.HttpStatus;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
public class APIStatus {

    private String message;
    private boolean success;
    private HttpStatus status;

}
