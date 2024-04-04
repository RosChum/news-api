package com.example.newsapi.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Email(message = "Некорректно введен email")
    private String email;

    @NotBlank(message = "Поле password не должно быть пустым")
    private String password;

}
