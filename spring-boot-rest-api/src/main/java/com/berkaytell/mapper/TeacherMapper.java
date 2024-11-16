package com.berkaytell.mapper;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
//Entity yani modeli DTO yapan sınıf
public class TeacherMapper {
    private ModelMapper modelMapper;

    public ModelMapper forResponse(){
        this.modelMapper.getConfiguration().setAmbiguityIgnored(true).setMatchingStrategy(MatchingStrategies.LOOSE);
        return this.modelMapper;
    }
}
