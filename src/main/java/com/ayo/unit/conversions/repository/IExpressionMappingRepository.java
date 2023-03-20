package com.ayo.unit.conversions.repository;


import com.ayo.unit.conversions.dto.ExpressionMapping;
import com.ayo.unit.conversions.dto.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IExpressionMappingRepository extends JpaRepository<ExpressionMapping, Integer> {
    Optional<ExpressionMapping> findOneByFromUnitAndToUnit(Unit fromUnit, Unit toUnit);
}
