package com.ayo.unit.conversions.repository;

import com.ayo.unit.conversions.dto.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUnitRepository extends JpaRepository<Unit, Integer> {
Optional<Unit> findOneByNameIgnoreCase(String name);

//Optional<Unit> findOneByNameEqualsIgnoreCase(String name);
}
