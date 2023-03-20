package com.ayo.unit.conversions.service.convert;

import com.ayo.unit.conversions.dto.Type;
import com.ayo.unit.conversions.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConversionStrategyService implements IConversionStrategyService {
    @Autowired
    private final TypeService typeService;
    @Autowired
    private final ConversionByFactorService conversionByFactorService;
    @Autowired
    private final ConversionByExpressionService conversionByExpressionService;

    public ConversionStrategyService(TypeService typeService, ConversionByFactorService conversionByFactorService, ConversionByExpressionService conversionByExpressionService) {
        this.typeService = typeService;
        this.conversionByFactorService = conversionByFactorService;
        this.conversionByExpressionService = conversionByExpressionService;
    }

    @Override
    public IConversionService getConversionService(String typeName){
        Type type = typeService.doesTypeExist(typeName);
        if(type.isExpression()){
            return conversionByExpressionService;
        }else{
            return conversionByFactorService;
        }
    }

}
