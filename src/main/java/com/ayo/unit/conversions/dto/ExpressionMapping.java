package com.ayo.unit.conversions.dto;

import javax.persistence.*;

@Table(name = "EXPRESSIONMAPPING")
@Entity
public class ExpressionMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL,optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "FROMUNIT", referencedColumnName = "ID")
    private Unit fromUnit;
    @ManyToOne(cascade = CascadeType.ALL,optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "TOUNIT", referencedColumnName = "ID")
    private Unit toUnit;
    @Column(name = "EQUATION")
    private String equation;

    public ExpressionMapping() {
    }

    public ExpressionMapping(Integer id, Unit fromUnit, Unit toUnit, String equation) {
        this.id = id;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.equation = equation;
    }

    public ExpressionMapping(Unit fromUnit, Unit toUnit, String equation) {
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.equation = equation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Unit getFromUnit() {
        return fromUnit;
    }

    public void setFromUnit(Unit fromUnit) {
        this.fromUnit = fromUnit;
    }

    public Unit getToUnit() {
        return toUnit;
    }

    public void setToUnit(Unit toUnit) {
        this.toUnit = toUnit;
    }

    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }
}
