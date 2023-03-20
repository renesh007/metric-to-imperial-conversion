package com.ayo.unit.conversions.service;

import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.repository.IUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UnitService {
    @Autowired
    private IUnitRepository unitRepository;

    public synchronized  Unit getUnitByName(String unitName) {
        return unitRepository.findOneByNameIgnoreCase(unitName)
                .orElseThrow(() -> new IllegalArgumentException("Conversion unit does not exist: " + unitName));
    }
    public  boolean  areUnitsOfSameType(Unit from, Unit to) {
        return from.getType().equals(to.getType());
    }
    public  boolean areUnitsOfSameSystem(Unit from, Unit to) {
       return from.getSystem().equals(to.getSystem());


    }
    public synchronized Unit getUnitOrNewUnit(String unitName, String system, Type type){

        Optional<Unit> unit =  unitRepository.findOneByNameIgnoreCase(unitName);

        return unit.orElseGet(() -> new Unit(unitName, system, type));

    }

    public Unit save(Unit unit){
            return  unitRepository.saveAndFlush(unit);
    }
}
