package com.ayo.unit.conversions.service;

import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.repository.ITypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TypeService {
    @Autowired
    ITypeRepository typeRepository;

    public Type doesTypeExist(String type){
        return typeRepository.findByNameIgnoreCase(type)
                .orElseThrow(() -> new IllegalArgumentException("Conversion Type does not exist -> "
                        + type + " the following are supported: "
                        + typeRepository.findByNameIsNotNull()
                        .stream()
                        .map(typeName -> typeName.getName())
                        .collect(Collectors.toList())
                ));
    }

    public Type getTypeOrNewType(String typeName,boolean isExpressive){
        Optional<Type> type = typeRepository.findByNameIgnoreCase(typeName);
        return type.orElseGet(() -> new Type(typeName,isExpressive));
    }
}
