package com.ayo.unit.conversions.service.add;

import com.ayo.unit.conversions.dto.FactorMapping;
import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.model.AddConversionRequest;
import com.ayo.unit.conversions.service.FactorMappingService;
import com.ayo.unit.conversions.service.TypeService;
import com.ayo.unit.conversions.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddConversionByFactorService implements IAddConversionService{
    @Autowired
    private FactorMappingService factorMappingService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UnitService unitService;
    public boolean add(AddConversionRequest candidate) {

        Type type = typeService.getTypeOrNewType(candidate.getConversionType(),false);

        Unit from = unitService.getUnitOrNewUnit(candidate.getSourceUnit(), candidate.getSourceUnitSystem(), type);
        Unit to = unitService.getUnitOrNewUnit(candidate.getDestinationUnit(), candidate.getDestinationUnitSystem(), type);

        if(!unitService.areUnitsOfSameType(from,to)){
            throw new IllegalArgumentException("Cannot convert between units of different types: " +
                    from.getType().getName() + " -> " + to.getType().getName());
        }

        if(unitService.areUnitsOfSameSystem(from,to)) {
            throw new IllegalArgumentException("Cannot convert units of the same system: " +
                    from.getSystem() + " -> " + to.getSystem());
        }
        from = unitService.save(from);
        to   = unitService.save(to);
        factorMappingService.doesMappingNotExistOrThrow(from,to);

        try{

            float parsedValue = Float.parseFloat(candidate.getConversionFactor());

            FactorMapping factorMapping =  factorMappingService.save(from,to,parsedValue);
            return factorMapping.getId()>0;
        }
        catch(NumberFormatException e){
            throw new NumberFormatException("Unable to parse Conversion Factor:" + candidate.getConversionFactor());
        }
    }

}
