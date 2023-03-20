package com.ayo.unit.conversions.dto;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "UNIT")
@Entity
@DynamicInsert
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "NAME")
    private String name;
    @Column(name = "SYSTEM")
    private String system;

    @OneToOne(cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    @JoinColumn(name = "TYPE", referencedColumnName = "ID")
    private Type type;

    public Unit() {
    }

    public Unit(Integer id, String name, String system, Type type) {
        this.id = id;
        this.name = name;
        this.system = system;
        this.type = type;
    }

    public Unit(String name, String system, Type type) {
        this.name = name;
        this.system = system;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return Objects.equals(id, unit.id) && (name.equalsIgnoreCase(unit.name) ) && (system.equalsIgnoreCase(unit.system)) && Objects.equals(type, unit.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, system, type);
    }
}
