package ru.otus.hw.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class UserDto {
    @NotBlank(message = "User username is can't be empty!")
    private String username;

    @NotBlank(message = "User password is can't be empty!")
    private String password;

    private Boolean enabled;
}
