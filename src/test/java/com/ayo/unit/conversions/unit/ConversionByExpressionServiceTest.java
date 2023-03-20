package com.ayo.unit.conversions.unit;

import com.ayo.unit.conversions.dto.ExpressionMapping;
import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.service.ExpressionMappingService;
import com.ayo.unit.conversions.service.UnitService;
import com.ayo.unit.conversions.service.convert.ConversionByExpressionService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
public class ConversionByExpressionServiceTest {
    private ConversionByExpressionService conversionByExpressionService;

    @Mock
    private UnitService unitService;

    @Mock
    private ExpressionMappingService expressionMappingService;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        conversionByExpressionService = new ConversionByExpressionService(unitService,expressionMappingService);

    }

    @Test
    public void testConvert() {
        Unit fromUnit = new Unit(1, "Celsius", "metric", new Type(1,"temperature",true));
        Unit toUnit = new Unit( 2,"Fahrenheit", "imperial", new Type(1,"temperature",true));

        ExpressionMapping expressionMapping = new ExpressionMapping(fromUnit, toUnit, "(i*1.8) + 32");

        when(unitService.getUnitByName(fromUnit.getName())).thenReturn(fromUnit);
        when(unitService.getUnitByName(toUnit.getName())).thenReturn(toUnit);
        when(unitService.areUnitsOfSameType(fromUnit,toUnit)).thenCallRealMethod();
        when(unitService.areUnitsOfSameSystem(fromUnit,toUnit)).thenCallRealMethod();
        when(expressionMappingService.doesMappingExistOrThrow(fromUnit, toUnit)).thenReturn(expressionMapping);

        double result1 = conversionByExpressionService.convert(fromUnit.getName(), toUnit.getName(), 10.0);
        assertEquals(50, result1, 0.00001);

        double result2 = conversionByExpressionService.convert(fromUnit.getName(), toUnit.getName(), 20.0);
        assertEquals(68, result2, 0.00001);

        verify(unitService, times(4)).getUnitByName(anyString());
        verify(expressionMappingService, times(2)).doesMappingExistOrThrow(any(Unit.class), any(Unit.class));

    }

    @Test
    public void testValidateValueWithOutOfRangeValue() {
        boolean result =  conversionByExpressionService.isValueWithinBounds(999999.0 +1);
        assertFalse(result);
    }

    @Test
    public void testValidateValueWithInRangeValue() {
        conversionByExpressionService.isValueWithinBounds(10.0);
    }

    @Test
    public void testExpressionResult() {
        Expression expression = mock(Expression.class);
        when(expression.getValue(Double.class)).thenReturn(5.0);

        double result = conversionByExpressionService.expressionResult(2.5, "2*i");

        assertEquals(5.0, result, 0.00001);

    }
}
