package ru.otus.hw.mappers;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;
import ru.otus.hw.models.User;

@Component
public class UserMapper {

    public User toModel(UserDto dto, PasswordEncoder encoder) {
        return new User(dto.getId(), dto.getUsername(),
                encoder.encode(dto.getPassword()), dto.getEnabled());
    }

    public User toModel(UserCreateDto dto, PasswordEncoder encoder) {
        return new User(null, dto.getUsername(), encoder.encode(dto.getPassword()), dto.getEnabled());
    }

    public User toModel(UserUpdateDto dto, PasswordEncoder encoder) {
        return new User(dto.getId(), dto.getUsername(),
                encoder.encode(dto.getPassword()), dto.getEnabled());
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getUsername(),
                user.getPassword(), user.getEnabled());
    }
}
