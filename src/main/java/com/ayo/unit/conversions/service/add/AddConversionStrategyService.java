package com.ayo.unit.conversions.service.add;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddConversionStrategyService implements IAddConversionStrategyService {
    @Autowired
    private final AddConversionByExpressionService addConversionByExpressionService;
    @Autowired
    private final AddConversionByFactorService addConversionByFactorService;

    public AddConversionStrategyService(AddConversionByExpressionService addConversionByExpressionService, AddConversionByFactorService addConversionByFactorService) {
        this.addConversionByExpressionService = addConversionByExpressionService;
        this.addConversionByFactorService = addConversionByFactorService;
    }

    @Override
    public IAddConversionService getAddConversionService(boolean isExpressive) {
        if(isExpressive){
            return addConversionByExpressionService;
        }else{
            return addConversionByFactorService;
        }
    }
}
