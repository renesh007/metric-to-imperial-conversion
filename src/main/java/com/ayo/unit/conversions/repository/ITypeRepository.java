package com.ayo.unit.conversions.repository;


import com.ayo.unit.conversions.dto.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ITypeRepository extends JpaRepository<Type, Integer> {
    Optional<Type> findByNameIgnoreCase(String name);
    List<Type>  findByNameIsNotNull();
}
