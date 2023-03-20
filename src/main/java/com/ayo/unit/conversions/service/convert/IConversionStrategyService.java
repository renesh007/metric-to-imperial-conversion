package com.ayo.unit.conversions.service.convert;

public interface IConversionStrategyService {
    IConversionService getConversionService(String typeName);
}
