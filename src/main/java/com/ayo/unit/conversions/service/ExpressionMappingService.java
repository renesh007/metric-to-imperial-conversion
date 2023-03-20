package com.ayo.unit.conversions.service;

import com.ayo.unit.conversions.dto.ExpressionMapping;
import com.ayo.unit.conversions.dto.Unit;

import com.ayo.unit.conversions.repository.IExpressionMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExpressionMappingService {
    @Autowired
    IExpressionMappingRepository expressionMappingRepository;
    public synchronized ExpressionMapping doesMappingExistOrThrow(Unit from, Unit to){


        return expressionMappingRepository
                .findOneByFromUnitAndToUnit(from, to)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Conversion does not exist between: " + from.getName() + " -> " + to.getName()));
    }

    public synchronized boolean doesMappingNotExistOrThrow(Unit from, Unit to){

        Optional<ExpressionMapping> expressionMapping = expressionMappingRepository
                .findOneByFromUnitAndToUnit(from, to);

        if(expressionMapping.isPresent()){
            throw new IllegalArgumentException("Conversion already exists between: "
                    + from.getName() + " -> " + to.getName());
        }
        else{
            return false;
        }

    }

    public synchronized ExpressionMapping save(Unit from,Unit to,String expression){
        ExpressionMapping expressionMapping = new ExpressionMapping(from,to,expression);

        return  expressionMappingRepository.saveAndFlush(expressionMapping);
    }
}
