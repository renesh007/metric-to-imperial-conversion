package com.ayo.unit.conversions.dto;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "TYPE")
@Entity
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "NAME")
    private String name;

    @Column(name = "ISEXPRESSION")
    private boolean isExpression;
    public Type() {
    }

    public Type(String name, boolean isExpression) {
        this.name = name;
        this.isExpression = isExpression;
    }

    public Type(Integer id, String name, boolean isExpression) {
        this.id = id;
        this.name = name;
        this.isExpression = isExpression;
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

    public boolean isExpression() {
        return isExpression;
    }

    public void setExpression(boolean expression) {
        isExpression = expression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return id.equals(type.id) && name.equals(type.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
