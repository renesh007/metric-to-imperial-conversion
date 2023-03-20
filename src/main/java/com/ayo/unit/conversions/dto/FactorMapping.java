package com.ayo.unit.conversions.dto;

import javax.persistence.*;

@Table(name = "FACTORMAPPING")
@Entity
public class FactorMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(cascade = CascadeType.ALL,optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "FROMUNIT", referencedColumnName = "ID")
    private Unit fromUnit;
    @ManyToOne(cascade = CascadeType.ALL,optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "TOUNIT", referencedColumnName = "ID")
    private Unit toUnit;
    @Column(name = "MULTIPLIER")
    private float multiplier;

    public FactorMapping() {
    }

    public FactorMapping(Integer id, Unit fromUnit, Unit toUnit, float multiplier) {
        this.id = id;
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.multiplier = multiplier;
    }

    public FactorMapping(Unit fromUnit, Unit toUnit, float multiplier) {
        this.fromUnit = fromUnit;
        this.toUnit = toUnit;
        this.multiplier = multiplier;
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

    public float getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(float multiplier) {
        this.multiplier = multiplier;
    }
}
