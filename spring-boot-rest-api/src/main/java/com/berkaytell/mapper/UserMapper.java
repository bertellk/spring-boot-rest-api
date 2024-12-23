package com.berkaytell.mapper;

import com.berkaytell.dto.user.SignUpUserDto;
import com.berkaytell.dto.user.UserDto;
import com.berkaytell.model.User;
import org.mapstruct.factory.Mappers;

public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);

    User toEntity(SignUpUserDto dto);

}
