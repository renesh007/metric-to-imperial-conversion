//package com.ayo.unit.conversions;
//
//import com.ayo.unit.conversions.dto.FactorMapping;
//import com.ayo.unit.conversions.dto.Type;
//import com.ayo.unit.conversions.dto.Unit;
//import com.ayo.unit.conversions.model.AddConversionRequest;
//import com.ayo.unit.conversions.repository.IConversionMappingRepository;
//import com.ayo.unit.conversions.repository.ITemperatureMappingRepository;
//import com.ayo.unit.conversions.repository.ITypeRepository;
//import com.ayo.unit.conversions.repository.IUnitRepository;
//import com.ayo.unit.conversions.service.ConversionService;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.expression.ExpressionParser;
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.when;
//
//public class ConversionServiceTest {
//
//    @Mock
//    private IUnitRepository unitRepository;
//
//    @Mock
//    private IConversionMappingRepository conversionMappingRepository;
//
//    @Mock
//    private ITemperatureMappingRepository temperatureMappingRepository;
//
//    @Mock
//    private ITypeRepository typeRepository;
//
//    @Mock
//    private ExpressionParser parser;
//
//    private ConversionService conversionService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//        conversionService = new ConversionService(parser,unitRepository,
//                conversionMappingRepository,temperatureMappingRepository,typeRepository);
//    }
//
//    @Test
//    public void testConvert_whenUnitsExist_shouldReturnCorrectResult() {
//        // arrange
//        Unit from = new Unit("meter", "metric", "m", new Type("Length"));
//        Unit to = new Unit("centimeter", "metric", "cm", new Type("Length"));
//        when(unitRepository.findOneByNameIgnoreCase("meter")).thenReturn(Optional.of(from));
//        when(unitRepository.findOneByNameIgnoreCase("centimeter")).thenReturn(Optional.of(to));
//
//        // act
//        double result = conversionService.convert("meter", "centimeter", "100");
//
//        // assert
//        assertEquals(10000.0, result, 0.0);
//    }
//
//    @Test
//    public void testConvert_whenFromUnitDoesNotExist_shouldThrowException() {
//        // arrange
//        when(unitRepository.findOneByNameIgnoreCase("meter")).thenReturn(Optional.empty());
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            conversionService.convert("meter", "centimeter", "100");
//        });
//
//    }
//
//    @Test
//    public void testConvert_whenToUnitDoesNotExist_shouldThrowException() {
//        // arrange
//        Unit from = new Unit("meter", "metric", "m", new Type("Length"));
//        when(unitRepository.findOneByNameIgnoreCase("meter")).thenReturn(Optional.of(from));
//        when(unitRepository.findOneByNameIgnoreCase("centimeter")).thenReturn(Optional.empty());
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            conversionService.convert("meter", "centimeter", "100");
//        });
//
//    }
//
//    @Test
//    public void testConvert_whenUnitsOfDifferentTypes_shouldThrowException() {
//        // arrange
//        Unit from = new Unit("meter", "metric", "m", new Type("Length"));
//        Unit to = new Unit("degree Celsius", "metric", "C", new Type("Temperature"));
//        when(unitRepository.findOneByNameIgnoreCase("meter")).thenReturn(Optional.of(from));
//        when(unitRepository.findOneByNameIgnoreCase("degree Celsius")).thenReturn(Optional.of(to));
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            conversionService.convert("meter", "degree Celsius", "100");
//        });
//
//    }
//
//    @Test
//    public void testConvert_whenValueIsNotANumber_shouldThrowException() {
//        // arrange
//        Unit from = new Unit("meter", "metric", "m", new Type("Length"));
//        Unit to = new Unit("centimeter", "metric", "cm", new Type("Length"));
//        when(unitRepository.findOneByNameIgnoreCase("meter")).thenReturn(Optional.of(from));
//        when(unitRepository.findOneByNameIgnoreCase("centimeter")).thenReturn(Optional.of(to));
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            conversionService.convert("meter", "centimeter", "abc");
//        });
//
//    }
//
//    @Test
//    public void testAdd() {
//        Type type = new Type("type1");
//        when(typeRepository.findByNameIgnoreCase("type1")).thenReturn(Optional.of(type));
//        when(unitRepository.findOneByNameIgnoreCase("unit1")).thenReturn(Optional.empty());
//        when(unitRepository.findOneByNameIgnoreCase("unit2")).thenReturn(Optional.empty());
//        when(conversionMappingRepository.findOneByFromUnitAndToUnit(null, null)).thenReturn(Optional.empty());
//        FactorMapping newMapping = conversionService.add(
//                new AddConversionRequest("type1", "unit1", "system1",
//                        "symbol1", "unit2", "system2",
//                        "symbol2", "2.0"));
//        assertNotNull(newMapping);
//    }
//
//    @Test
//    public void testAddThrowsIllegalArgumentException() {
//        Type type = new Type("type1");
//        when(typeRepository.findByNameIgnoreCase("type1")).thenReturn(Optional.of(type));
//        when(unitRepository.findOneByNameIgnoreCase("unit1")).thenReturn(Optional.empty());
//        when(unitRepository.findOneByNameIgnoreCase("unit2")).thenReturn(Optional.empty());
//        FactorMapping existingMapping = new FactorMapping(new Unit("unit1", "system1", "symbol1", type), new Unit("unit2", "system2", "symbol2", type), 1.0f);
//        when(conversionMappingRepository.findOneByFromUnitAndToUnit(null, null)).thenReturn(Optional.of(existingMapping));
//        assertThrows(IllegalArgumentException.class, () -> {
//            conversionService.add(new AddConversionRequest("type1", "unit1", "system1", "symbol1", "unit2", "system2", "symbol2", "2.0"));
//        });
//    }
//}
