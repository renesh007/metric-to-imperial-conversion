package com.ayo.unit.conversions.controller;

import com.ayo.unit.conversions.model.AddConversionRequest;
import com.ayo.unit.conversions.model.ConversionRequest;
import com.ayo.unit.conversions.service.add.IAddConversionService;
import com.ayo.unit.conversions.service.add.IAddConversionStrategyService;
import com.ayo.unit.conversions.service.convert.IConversionService;
import com.ayo.unit.conversions.service.convert.IConversionStrategyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.Arrays;

@RestController
@Validated
public class ConversionController {
    private static final Logger logger = LoggerFactory.getLogger(ConversionController.class);
    @Autowired
    IConversionStrategyService conversionService;

    @Autowired
    IAddConversionStrategyService addConversionStrategyService;

    @GetMapping("/ping")
    public ResponseEntity<String> ping(){
        return  ResponseEntity.ok("alive");
    }

    @PostMapping("api/{type}/convert")
    public ResponseEntity<?> convertArea(@RequestBody  @Valid ConversionRequest conversionRequestBody,
                                         @PathVariable @NotEmpty String type) {
        try {
            logger.info("New Convert Request: " + conversionRequestBody.toString());
            IConversionService iConversionService =  conversionService.getConversionService(type);
            double response = iConversionService.convert(conversionRequestBody.getSourceUnit(),
                    conversionRequestBody.getDestinationUnit(),conversionRequestBody.getValue());
            return ResponseEntity.ok(response);
        } catch(Exception e) {
            logger.error("EXCEPTION: " + Arrays.toString(e.getStackTrace()));
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("api/add")
    public ResponseEntity<?> addConversionByFactor(@RequestBody @Valid AddConversionRequest addConversionRequest) {

        try {
            logger.info("New Add Request: " + addConversionRequest.toString());
            IAddConversionService addConversionService = addConversionStrategyService.
                    getAddConversionService(addConversionRequest.isExpression());

            return ResponseEntity.ok(addConversionService.add(addConversionRequest));
        } catch(Exception e) {
            logger.error("EXCEPTION: " + Arrays.toString(e.getStackTrace()));
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }




}
