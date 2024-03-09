package ru.otus.hw.services;

import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto findByUsername(String username);

    UserDto createUser(UserCreateDto createDto);

    UserDto updateUser(UserUpdateDto updateDto);

    void deleteUser(UserDto dto);
}
