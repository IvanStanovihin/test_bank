package com.example.bank.domain.mapper;

import com.example.bank.domain.dtos.UserDto;
import com.example.bank.domain.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

  UserDto toDto(User user);

}
