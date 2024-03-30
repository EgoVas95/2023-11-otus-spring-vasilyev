package ru.otus.hw.services;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.mappers.UserMapper;
import ru.otus.hw.repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    private final UserMapper mapper;

    private final PasswordEncoder encoder;

    @Override
    public List<UserDto> findAll() {
        return repository.findAll()
                .stream().map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findByUsername(String username) {

        return mapper.toDto(repository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User with username = %s is not found"
                        .formatted(username))));
    }

    @Override
    public UserDto createUser(UserCreateDto createDto) {
        var user = mapper.toModel(createDto, encoder);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public UserDto updateUser(UserUpdateDto updateDto) {
        var user = mapper.toModel(updateDto, encoder);
        return mapper.toDto(repository.save(user));
    }

    @Override
    public void deleteUser(@NotNull UserDto dto) {
        repository.delete(mapper.toModel(dto, encoder));
    }
}
