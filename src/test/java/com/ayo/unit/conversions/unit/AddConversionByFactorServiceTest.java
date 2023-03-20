package com.ayo.unit.conversions.unit;

import com.ayo.unit.conversions.dto.FactorMapping;
import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.model.AddConversionRequest;
import com.ayo.unit.conversions.service.FactorMappingService;
import com.ayo.unit.conversions.service.TypeService;
import com.ayo.unit.conversions.service.UnitService;
import com.ayo.unit.conversions.service.add.AddConversionByFactorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
public class AddConversionByFactorServiceTest {
    @Mock
    private FactorMappingService factorMappingService;

    @Mock
    private TypeService typeService;

    @Mock
    private UnitService unitService;

    @InjectMocks
    private AddConversionByFactorService addConversionByFactorService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void add_ValidFactor_Success() {
        // Arrange
        AddConversionRequest request = new AddConversionRequest();
        request.setConversionType("Length");
        request.setSourceUnit("Meter");
        request.setDestinationUnit("Yard");
        request.setSourceUnitSystem("Metric");
        request.setDestinationUnitSystem("Imperial");
        request.setConversionFactor("0.001");

        Type type = new Type("Length", false);
        Unit meter = new Unit("Meter", "Metric", type);
        Unit yard = new Unit("Yard", "Imperial", type);

        when(typeService.getTypeOrNewType(request.getConversionType(), false)).thenReturn(type);
        when(unitService.getUnitOrNewUnit(request.getSourceUnit(), request.getSourceUnitSystem(), type)).thenReturn(meter);
        when(unitService.getUnitOrNewUnit(request.getDestinationUnit(), request.getDestinationUnitSystem(), type))
                .thenReturn(yard);
        when(unitService.areUnitsOfSameType(meter, yard)).thenCallRealMethod();
        when(unitService.areUnitsOfSameSystem(meter, yard)).thenCallRealMethod();
        when(unitService.save(meter)).thenReturn(meter);
        when(unitService.save(yard)).thenReturn(yard);
        when(factorMappingService.doesMappingNotExistOrThrow(meter, yard)).thenReturn(true);
        float parsedValue = Float.parseFloat(request.getConversionFactor());
        when(factorMappingService.save(meter, yard, parsedValue))
                .thenReturn(new FactorMapping(1, meter, yard, parsedValue));

        // Act
        boolean result = addConversionByFactorService.add(request);

        // Assert
        assertTrue(result);
    }

    @Test
    public void add_InvalidFactor_Error() {
        // Arrange
        AddConversionRequest request = new AddConversionRequest();
        request.setConversionType("Length");
        request.setSourceUnit("Meter");
        request.setDestinationUnit("Yard");
        request.setSourceUnitSystem("Metric");
        request.setDestinationUnitSystem("Metric");
        request.setConversionFactor("0.001");

        Type type = new Type("Length", false);
        Unit meter = new Unit("Meter", "Metric", type);
        Unit yard = new Unit("Yard", "Metric", type);

        when(typeService.getTypeOrNewType(request.getConversionType(), false)).thenReturn(type);
        when(unitService.getUnitOrNewUnit(request.getSourceUnit(), request.getSourceUnitSystem(), type)).thenReturn(meter);
        when(unitService.getUnitOrNewUnit(request.getDestinationUnit(), request.getDestinationUnitSystem(), type))
                .thenReturn(yard);
        when(unitService.areUnitsOfSameType(meter, yard)).thenCallRealMethod();
        when(unitService.areUnitsOfSameSystem(meter, yard)).thenCallRealMethod();
        when(unitService.save(meter)).thenReturn(meter);
        when(unitService.save(yard)).thenReturn(yard);
        float parsedValue = Float.parseFloat(request.getConversionFactor());
        when(factorMappingService.save(meter, yard, parsedValue))
                .thenReturn(new FactorMapping(1, meter, yard, parsedValue));


        assertThrows(IllegalArgumentException.class, ()->addConversionByFactorService.add(request));
    }
    @Test
    public void add_shouldThrowNumberFormatException_whenConversionFactorIsInvalid() {
        AddConversionRequest request = new AddConversionRequest();
        request.setConversionType("Length");
        request.setSourceUnit("Meter");
        request.setDestinationUnit("Yard");
        request.setSourceUnitSystem("Metric");
        request.setDestinationUnitSystem("Imperial");
        request.setConversionFactor("invalid");

        Type type = new Type(1,"Length", false);
        Unit meter = new Unit(1,"Meter", "Metric", type);
        Unit yard = new Unit(2,"Yard", "Imperial", type);

        when(typeService.getTypeOrNewType(type.getName(), false)).thenReturn(type);
        when(unitService.getUnitOrNewUnit(meter.getName(), meter.getSystem(), type)).thenReturn(meter);
        when(unitService.getUnitOrNewUnit(yard.getName(), yard.getSystem(), type)).thenReturn(yard);
        when(unitService.areUnitsOfSameType(meter, yard)).thenCallRealMethod();
        when(unitService.areUnitsOfSameSystem(meter, yard)).thenCallRealMethod();

        assertThrows(NumberFormatException.class, () -> addConversionByFactorService.add(request));

    }
}
