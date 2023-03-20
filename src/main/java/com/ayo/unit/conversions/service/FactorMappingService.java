package com.ayo.unit.conversions.service;

import com.ayo.unit.conversions.dto.FactorMapping;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.repository.IFactorMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FactorMappingService {
@Autowired
    IFactorMappingRepository factorMappingRepository;
    public FactorMapping doesMappingExistOrThrow(Unit from, Unit to){
        return factorMappingRepository
                .findOneByFromUnitAndToUnit(from, to)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Conversion does not exist between: " + from.getName() + " -> " + to.getName()));
    }
    public boolean doesMappingNotExistOrThrow(Unit from, Unit to){

        Optional<FactorMapping> factorMapping = factorMappingRepository
                .findOneByFromUnitAndToUnit(from, to);

        if(factorMapping.isPresent()){
            throw new IllegalArgumentException("Conversion already exists between: "
                    + from.getName() + " -> " + to.getName());
        }
        else{
            return false;
        }

    }

    public FactorMapping save(Unit from,Unit to,float value){
        FactorMapping newMapping = new FactorMapping(from,to,value);

        return  factorMappingRepository.saveAndFlush(newMapping);
    }
}
