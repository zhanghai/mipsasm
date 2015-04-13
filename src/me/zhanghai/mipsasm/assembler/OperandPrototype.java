/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class OperandPrototype {

    private String name;
    private OperandType type;

    protected OperandPrototype(String name, OperandType type) {
        this.name = name;
        this.type = type;
    }

    public static OperandPrototype of(String name, OperandType type) {
        return new OperandPrototype(name, type);
    }

    public String getName() {
        return name;
    }

    public OperandType getType() {
        return type;
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        }

        OperandPrototype that = (OperandPrototype) object;
        if (!name.equals(that.name)) {
            return false;
        } else if (type != that.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OperandPrototype {" +
                "name='" + name + "'" +
                ", type=" + type +
                '}';
    }
}
