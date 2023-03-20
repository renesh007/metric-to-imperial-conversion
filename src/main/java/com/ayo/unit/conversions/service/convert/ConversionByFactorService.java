package com.ayo.unit.conversions.service.convert;

import com.ayo.unit.conversions.dto.FactorMapping;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.service.FactorMappingService;
import com.ayo.unit.conversions.service.TypeService;
import com.ayo.unit.conversions.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionByFactorService extends AbstractConversion {
    @Autowired
    private final FactorMappingService factorMappingService;
    @Autowired
    private final TypeService typeService;
    @Autowired
    private final UnitService unitService;

    public ConversionByFactorService(FactorMappingService factorMappingService, TypeService typeService, UnitService unitService) {
        this.factorMappingService = factorMappingService;
        this.typeService = typeService;
        this.unitService = unitService;
    }

    @Override
    public double convert(String fromUnit, String toUnit, double value) {
        if(isUnitNameEmpty(fromUnit)){
            throw new IllegalArgumentException("Source Unit Name cannot be blank");
        }
        if(isUnitNameEmpty(toUnit)){
            throw new IllegalArgumentException("Destination Unit Name cannot be blank");
        }

        if(!isValueWithinBounds(value)){
            throw new IllegalArgumentException("Value is outside of the expected range: " +
                    "" + 1 +  "->" + Double.MAX_VALUE);
        }

        Unit from = unitService.getUnitByName(fromUnit);
        Unit to = unitService.getUnitByName(toUnit);

        if(!unitService.areUnitsOfSameType(from,to)){
            throw new IllegalArgumentException("Cannot convert between units of different types: " +
                    from.getType().getName() + " -> " + to.getType().getName());
        }

        if(unitService.areUnitsOfSameSystem(from,to)) {
            throw new IllegalArgumentException("Cannot convert units of the same system: " +
                    from.getSystem() + " -> " + to.getSystem());
        }

        FactorMapping conversionMapping = factorMappingService.doesMappingExistOrThrow(from,to);

        return multiplicativeResult(value,conversionMapping.getMultiplier());

    }
    public double multiplicativeResult(double value, double factor){
        return value * factor;
    }


}
