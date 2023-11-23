package ru.acceleration.store.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.acceleration.store.dto.user.UserCreateDto;
import ru.acceleration.store.dto.user.UserDto;
import ru.acceleration.store.model.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    UserDto toUserDto(UserCreateDto userCreateDto);

    User toUser(UserDto userDto);

    UserDto toUserDto(User user);
}
