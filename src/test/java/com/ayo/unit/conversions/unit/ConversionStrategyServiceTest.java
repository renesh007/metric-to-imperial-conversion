package com.ayo.unit.conversions.unit;
import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.service.TypeService;
import com.ayo.unit.conversions.service.convert.ConversionByExpressionService;
import com.ayo.unit.conversions.service.convert.ConversionByFactorService;
import com.ayo.unit.conversions.service.convert.ConversionStrategyService;
import com.ayo.unit.conversions.service.convert.IConversionService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ConversionStrategyServiceTest {
    @Mock
    private TypeService typeService;

    @Mock
    private ConversionByFactorService conversionByFactorService;

    @Mock
    private ConversionByExpressionService conversionByExpressionService;

    @InjectMocks
    private ConversionStrategyService conversionStrategyService;

    public ConversionStrategyServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getConversionService_whenTypeIsExpression_shouldReturnConversionByExpressionService() {
        String typeName = "some type name";
        Type expressionType = new Type(typeName,true);

        when(typeService.doesTypeExist(typeName)).thenReturn(expressionType);
        IConversionService result = conversionStrategyService.getConversionService(typeName);
        assertEquals(result, conversionByExpressionService);
        verify(typeService).doesTypeExist(typeName);
        verifyNoMoreInteractions(typeService);
        verifyNoInteractions(conversionByFactorService);
    }

    @Test
    public void getConversionService_whenTypeIsNotExpression_shouldReturnConversionByFactorService() {
        String typeName = "some type name";
        Type factorType = new Type();
        factorType.setExpression(false);
        when(typeService.doesTypeExist(typeName)).thenReturn(factorType);
        IConversionService result = conversionStrategyService.getConversionService(typeName);
        assertEquals(result, conversionByFactorService);
        verify(typeService).doesTypeExist(typeName);
        verifyNoMoreInteractions(typeService);
        verifyNoInteractions(conversionByExpressionService);
    }
}
