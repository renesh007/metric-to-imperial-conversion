package com.ayo.unit.conversions.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;


public class AddConversionRequest {
    @JsonProperty(value = "Source Unit",required = true)
    @NotEmpty(message = "Source Unit cannot be Empty")
    private String sourceUnit;
    @JsonProperty(value = "Source Unit System",required = true)
    @NotEmpty(message = "Source Unit System cannot be Empty")
    private String sourceUnitSystem;

    @JsonProperty(value = "Destination Unit",required = true)
    @NotEmpty(message = "Destination Unit cannot be Empty")
    private String destinationUnit;
    @JsonProperty(value = "Destination Unit System",required = true)
    @NotEmpty(message = "Destination Unit System cannot be Empty")
    private String destinationUnitSystem;

    @JsonProperty(value = "Conversion Type",required = true)
    @NotEmpty(message = "Conversion Type cannot be Empty")
    private String conversionType;
    @JsonProperty(value = "Conversion Factor",required = true)
    @NotEmpty(message = "Conversion Factor cannot be Empty")
    private String conversionFactor;
    @JsonProperty(value = "Is Expression",required = true)
    @NotNull(message = "Is Expression cannot be Null: True or False expected")
    private boolean isExpression;


    public AddConversionRequest() {
    }

    public AddConversionRequest(String sourceUnit, String sourceUnitSystem, String destinationUnit, String destinationUnitSystem, String conversionType, String conversionFactor, boolean isExpression) {
        this.sourceUnit = sourceUnit;
        this.sourceUnitSystem = sourceUnitSystem;
        this.destinationUnit = destinationUnit;
        this.destinationUnitSystem = destinationUnitSystem;
        this.conversionType = conversionType;
        this.conversionFactor = conversionFactor;
        this.isExpression = isExpression;
    }
    public String getSourceUnit() {
        return sourceUnit;
    }

    public void setSourceUnit(String sourceUnit) {
        this.sourceUnit = sourceUnit;
    }

    public String getSourceUnitSystem() {
        return sourceUnitSystem;
    }

    public void setSourceUnitSystem(String sourceUnitSystem) {
        this.sourceUnitSystem = sourceUnitSystem;
    }

    public String getDestinationUnit() {
        return destinationUnit;
    }

    public void setDestinationUnit(String destinationUnit) {
        this.destinationUnit = destinationUnit;
    }

    public String getDestinationUnitSystem() {
        return destinationUnitSystem;
    }

    public void setDestinationUnitSystem(String destinationUnitSystem) {
        this.destinationUnitSystem = destinationUnitSystem;
    }

    public String getConversionType() {
        return conversionType;
    }

    public void setConversionType(String conversionType) {
        this.conversionType = conversionType;
    }

    public String getConversionFactor() {
        return conversionFactor;
    }

    public void setConversionFactor(String conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public boolean isExpression() {
        return isExpression;
    }

    public void setExpression(boolean expression) {
        isExpression = expression;
    }

    @Override
    public String toString() {
        return "AddConversionRequest{" +
                "sourceUnit='" + sourceUnit + '\'' +
                ", sourceUnitSystem='" + sourceUnitSystem + '\'' +
                ", destinationUnit='" + destinationUnit + '\'' +
                ", destinationUnitSystem='" + destinationUnitSystem + '\'' +
                ", conversionType='" + conversionType + '\'' +
                ", conversionFactor='" + conversionFactor + '\'' +
                ", isExpression=" + isExpression +
                '}';
    }
}
