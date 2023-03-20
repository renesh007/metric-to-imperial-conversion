package com.ayo.unit.conversions.service.convert;

public abstract class AbstractConversion implements IConversionService{


    public boolean isUnitNameEmpty(String value){
        return (value == null || value.isEmpty());
    }
    public boolean isValueWithinBounds(double value){
        return ((value >= 0.0  ) || (value <= Double.MAX_VALUE));
    }

}
