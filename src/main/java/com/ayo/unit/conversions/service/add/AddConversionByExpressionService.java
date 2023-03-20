package com.ayo.unit.conversions.service.add;

import com.ayo.unit.conversions.dto.ExpressionMapping;
import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.model.AddConversionRequest;
import com.ayo.unit.conversions.service.ExpressionMappingService;
import com.ayo.unit.conversions.service.TypeService;
import com.ayo.unit.conversions.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

@Service
public class AddConversionByExpressionService implements IAddConversionService {
    @Autowired
    private ExpressionMappingService expressionMappingService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private UnitService unitService;

    private final ExpressionParser parser;

    public AddConversionByExpressionService() {
        this.parser = new SpelExpressionParser();
    }
    @Override
    public boolean add(AddConversionRequest candidate) {
        try{


        Type type = typeService.getTypeOrNewType(candidate.getConversionType(),true);

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
        expressionMappingService.doesMappingNotExistOrThrow(from,to);


            Expression expression = parser.parseExpression(candidate.getConversionFactor());

            ExpressionMapping expressionMapping =  expressionMappingService.save(from,to,candidate.getConversionFactor());

            return expressionMapping.getId() >0;
        }
        catch(ParseException e){
            throw new ParseException(e.getPosition(),
                    "Unable to parse Conversion Factor:" + candidate.getConversionFactor() +
                            " Error at position:" + e.getPosition());
        }
    }

}
