package com.example.newsapi.dto;

import com.example.newsapi.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationUserDto {

    @NotBlank(message = "Имя не должно быть пустым")
    private String firstName;

    private String lastName;

    @Email(message = "введен некорректный email")
    private String email;

    @Size(min = 4, max = 6, message = "Пароль должен содержать не менее 4 и не более 6 символов")
    private String password;

    private Set<Role> roles = new HashSet<>();
}
