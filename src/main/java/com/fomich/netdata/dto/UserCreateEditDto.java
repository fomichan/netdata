package com.fomich.netdata.dto;

import com.fomich.netdata.database.entity.Role;
import com.fomich.netdata.validation.MuxUniqueName;
import com.fomich.netdata.validation.UserUniqueName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import lombok.experimental.FieldNameConstants;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Value
@FieldNameConstants
@UserUniqueName // валидация на уникальное имя
public class UserCreateEditDto {

    @Email(message = "Имя пользователя должно быть в формате email")
    String username;

    @NotBlank(message = "Пароль не должен быть пустым")
    String rawPassword;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate birthDate;

    @Size(min = 3, max = 64, message = "Длина имени должна быть от 3 до 64 символов")
    String firstname;

    @Size(min = 3, max = 64, message = "Длина фамилии должна быть от 3 до 64 символов")
    String lastname;

    Role role;


}
