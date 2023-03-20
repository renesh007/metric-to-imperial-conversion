package com.ayo.unit.conversions.service.convert;

import com.ayo.unit.conversions.dto.ExpressionMapping;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.service.ExpressionMappingService;
import com.ayo.unit.conversions.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Service;

@Service
public class ConversionByExpressionService extends AbstractConversion{

    @Autowired
    private UnitService unitService;
    @Autowired
    private ExpressionMappingService expressionMappingService;

    private final ExpressionParser parser;

    private static final double MIN = -999999.0;
    private static final double MAX = 999999.0;

    public ConversionByExpressionService() {
        this.parser = new SpelExpressionParser();
    }


    public ConversionByExpressionService(UnitService unitService, ExpressionMappingService expressionMappingService) {
        this.unitService = unitService;
        this.expressionMappingService = expressionMappingService;
        this.parser = new SpelExpressionParser();
    }

    @Override
    public double convert(String fromUnit, String toUnit, double value) throws EvaluationException,NullPointerException{
        if(isUnitNameEmpty(fromUnit)){
            throw new IllegalArgumentException("Source Unit Name cannot be blank");
        }
        if(isUnitNameEmpty(toUnit)){
            throw new IllegalArgumentException("Destination Unit Name cannot be blank");
        }


        if(!isValueWithinBounds(value)){
            throw new IllegalArgumentException("Value is outside of the expected range: " +
                     MIN +  " -> " + MAX);
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

        ExpressionMapping expressionMapping = expressionMappingService.doesMappingExistOrThrow(from,to);

        return expressionResult(value, expressionMapping.getEquation());
    }
    public double expressionResult(double value, String formula)throws EvaluationException,NullPointerException{
        double result;

        Expression expression = parser.parseExpression(formula.replaceFirst("i", String.valueOf(value)));
        result = expression.getValue(Double.class);

        return result;
    }

    @Override
    public boolean isValueWithinBounds(double value) {
        return ((value >= MIN) && (value <= MAX));
    }
}
