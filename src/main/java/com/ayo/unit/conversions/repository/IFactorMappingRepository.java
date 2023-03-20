package com.ayo.unit.conversions.repository;


import com.ayo.unit.conversions.dto.FactorMapping;
import com.ayo.unit.conversions.dto.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IFactorMappingRepository extends JpaRepository<FactorMapping, Integer> {

    Optional<FactorMapping> findOneByFromUnitAndToUnit(Unit fromUnit, Unit toUnit);
}
