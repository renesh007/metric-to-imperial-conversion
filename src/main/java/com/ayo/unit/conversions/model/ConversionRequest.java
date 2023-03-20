package com.ayo.unit.conversions.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ConversionRequest {
    @JsonProperty(value = "Source Unit",required = true)
    @NotEmpty(message ="Source Unit missing")
    private String sourceUnit;
    @JsonProperty(value = "Destination Unit",required = true)
    @NotEmpty(message ="Destination Unit missing")
    private String destinationUnit;
    @JsonProperty(value = "Value",required = true)
    @NotNull(message ="Value Unit missing")
    private Double value;

    public ConversionRequest() {
    }

    public ConversionRequest(String sourceUnit, String destinationUnit, double value) {
        this.sourceUnit = sourceUnit;
        this.destinationUnit = destinationUnit;
        this.value = value;
    }

    public String getSourceUnit() {
        return sourceUnit;
    }

    public void setSourceUnit(String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    public String getDestinationUnit() {
        return destinationUnit;
    }

    public void setDestinationUnit(String destinationUnit) {
        this.destinationUnit = destinationUnit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "ConversionRequest{" +
                "sourceUnit='" + sourceUnit + '\'' +
                ", destinationUnit='" + destinationUnit + '\'' +
                ", value=" + value +
                '}';
    }
}
