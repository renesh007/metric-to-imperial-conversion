package com.ayo.unit.conversions.unit;
import com.ayo.unit.conversions.dto.ExpressionMapping;
import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.model.AddConversionRequest;
import com.ayo.unit.conversions.service.ExpressionMappingService;
import com.ayo.unit.conversions.service.TypeService;
import com.ayo.unit.conversions.service.UnitService;
import com.ayo.unit.conversions.service.add.AddConversionByExpressionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
public class AddConversionByExpressionServiceTest {
    @Mock
    private ExpressionMappingService expressionMappingService;

    @Mock
    private TypeService typeService;

    @Mock
    private UnitService unitService;

    @InjectMocks
    private AddConversionByExpressionService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void add_ValidExpression_Success() {
        // Arrange
        AddConversionRequest request = new AddConversionRequest();
        request.setConversionType("Length");
        request.setSourceUnit("Meter");
        request.setDestinationUnit("Yard");
        request.setSourceUnitSystem("Metric");
        request.setDestinationUnitSystem("Imperial");
        request.setConversionFactor("0.001");

        Type type = new Type("Length", true);
        Unit meter = new Unit("Meter", "Metric", type);
        Unit yard = new Unit("Yard", "Imperial", type);

        when(typeService.getTypeOrNewType(request.getConversionType(), true)).thenReturn(type);
        when(unitService.getUnitOrNewUnit(request.getSourceUnit(), request.getSourceUnitSystem(), type)).thenReturn(meter);
        when(unitService.getUnitOrNewUnit(request.getDestinationUnit(), request.getDestinationUnitSystem(), type))
                .thenReturn(yard);
        when(unitService.areUnitsOfSameType(meter, yard)).thenCallRealMethod();
        when(unitService.areUnitsOfSameSystem(meter, yard)).thenCallRealMethod();
        when(unitService.save(meter)).thenReturn(meter);
        when(unitService.save(yard)).thenReturn(yard);
        when(expressionMappingService.doesMappingNotExistOrThrow(meter, yard)).thenReturn(true);
        when(expressionMappingService.save(meter, yard, request.getConversionFactor()))
                .thenReturn(new ExpressionMapping(1, meter, yard, request.getConversionFactor()));

        // Act
        boolean result = service.add(request);

        // Assert
        assertTrue(result);
    }

    @Test
    public void add_InvalidExpression_Error() {
        // Arrange
        AddConversionRequest request = new AddConversionRequest();
        request.setConversionType("Length");
        request.setSourceUnit("Meter");
        request.setDestinationUnit("Yard");
        request.setSourceUnitSystem("Metric");
        request.setDestinationUnitSystem("Metric");
        request.setConversionFactor("0.001");

        Type type = new Type("Length", true);
        Unit meter = new Unit("Meter", "Metric", type);
        Unit yard = new Unit("Yard", "Metric", type);

        when(typeService.getTypeOrNewType(request.getConversionType(), true)).thenReturn(type);
        when(unitService.getUnitOrNewUnit(request.getSourceUnit(), request.getSourceUnitSystem(), type)).thenReturn(meter);
        when(unitService.getUnitOrNewUnit(request.getDestinationUnit(), request.getDestinationUnitSystem(), type))
                .thenReturn(yard);
        when(unitService.areUnitsOfSameType(meter, yard)).thenCallRealMethod();
        when(unitService.areUnitsOfSameSystem(meter, yard)).thenCallRealMethod();
        when(unitService.save(meter)).thenReturn(meter);
        when(unitService.save(yard)).thenReturn(yard);
        when(expressionMappingService.doesMappingNotExistOrThrow(meter, yard)).thenReturn(true);
        when(expressionMappingService.save(meter, yard, request.getConversionFactor()))
                .thenReturn(new ExpressionMapping(1, meter, yard, request.getConversionFactor()));


        assertThrows(IllegalArgumentException.class, ()->service.add(request));
    }
}
