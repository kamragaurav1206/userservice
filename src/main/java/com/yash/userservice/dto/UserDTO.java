package com.yash.userservice.dto;

import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {


    @Size(min = 5,max = 10,message ="username should not be less then 5 char and user name should not be more then 10 char" )
    private String userName;

    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "about section should not be null")

    private String about;
}
