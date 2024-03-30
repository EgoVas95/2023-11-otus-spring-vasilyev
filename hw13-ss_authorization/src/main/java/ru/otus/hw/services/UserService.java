package ru.otus.hw.services;

import org.springframework.security.access.prepost.PreAuthorize;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;

import java.util.List;

public interface UserService {
    @PreAuthorize("hasRole('ADMIN')")
    List<UserDto> findAll();

    @PreAuthorize("hasRole('ADMIN')")
    UserDto findByUsername(String username);

    @PreAuthorize("hasRole('ADMIN')")
    UserDto createUser(UserCreateDto createDto);

    @PreAuthorize("hasRole('ADMIN')")
    UserDto updateUser(UserUpdateDto updateDto);

    @PreAuthorize("hasRole('ADMIN')")
    void deleteUser(UserDto dto);
}
