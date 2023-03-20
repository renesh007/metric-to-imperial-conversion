package com.ayo.unit.conversions.unit;

import com.ayo.unit.conversions.dto.FactorMapping;
import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.dto.Unit;
import com.ayo.unit.conversions.service.FactorMappingService;
import com.ayo.unit.conversions.service.TypeService;
import com.ayo.unit.conversions.service.UnitService;
import com.ayo.unit.conversions.service.convert.ConversionByFactorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
public class ConversionByFactorServiceTest {
    @Mock
    private FactorMappingService factorMappingService;
    @Mock
    private TypeService typeService;
    @Mock
    private UnitService unitService;

    private ConversionByFactorService conversionByFactorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        conversionByFactorService = new ConversionByFactorService(factorMappingService,typeService,unitService);
    }

    @Test
    void testConversionWithValidInput() {
        Unit unit1 = new Unit( 1,"meter", "metric", new Type(1,"length",false));
        Unit unit2 = new Unit( 2,"yard", "imperial", new Type(1,"length",false));
        float factor = 1.094f;
        when(unitService.getUnitByName("meter")).thenReturn(unit1);
        when(unitService.getUnitByName("yard")).thenReturn(unit2);
        when(unitService.areUnitsOfSameType(unit1,unit2)).thenCallRealMethod();
        when(unitService.areUnitsOfSameSystem(unit1,unit2)).thenCallRealMethod();

        when(factorMappingService.doesMappingExistOrThrow(any(), any())).thenReturn(new FactorMapping(unit1,unit2,factor));

        double result = conversionByFactorService.convert("meter", "yard", 5000);

        assertEquals(5470, result, 1.0);
    }

    @Test
    void testConversion_whenSystemsAreEqual_shouldThrowException() {
        Unit unit1 = new Unit( 1,"meter", "metric", new Type(1,"length",false));
        Unit unit2 = new Unit( 2,"yard", "metric", new Type(1,"length",false));
        float factor = 1.094f;
        when(factorMappingService.doesMappingExistOrThrow(any(), any())).thenReturn(new FactorMapping(unit1,unit2,factor));
        assertThrows(IllegalArgumentException.class, () -> {
            conversionByFactorService.convert("meter", "", 5000);
        });
    }

    @Test
    public void testConversion_whenUnitsOfDifferentTypes_shouldThrowException() {
        // arrange
        Unit from = new Unit("meter", "metric",  new Type("Length",false));
        Unit to = new Unit("degree Celsius", "metric",  new Type("Temperature",false));
        float factor = 1.094f;
        when(unitService.getUnitByName("meter")).thenReturn(from);
        when(unitService.getUnitByName("degree Celsius")).thenReturn(to);
        //when(unitService.areUnitsOfSameType(from,to)).thenReturn();
        when(factorMappingService.doesMappingExistOrThrow(any(), any())).thenReturn(new FactorMapping(from,to,factor));
        assertThrows(IllegalArgumentException.class, () -> {
            conversionByFactorService.convert("meter", "degree Celsius", 100.0);
        });

    }

    @Test
    void testMultiplicativeResult() {
        double result = conversionByFactorService.multiplicativeResult(5.0, 1000);

        assertEquals(5000, result, 0.01);
    }
}
