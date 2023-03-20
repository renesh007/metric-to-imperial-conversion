package com.ayo.unit.conversions.service.add;

import com.ayo.unit.conversions.model.AddConversionRequest;

public interface IAddConversionService {
    boolean add(AddConversionRequest candidate);
}
