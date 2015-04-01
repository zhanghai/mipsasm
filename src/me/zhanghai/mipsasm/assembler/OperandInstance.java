/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

public class OperandInstance extends OperandPrototype {

    private Operand operand;

    private OperandInstance(String name, OperandType type, Operand operand) {
        super(name, type);

        this.operand = operand;
    }

    public static OperandInstance fromPrototype(OperandPrototype operandPrototype, Operand operand) {
        return new OperandInstance(operandPrototype.getName(), operandPrototype.getType(), operand);
    }

    public Operand getOperand() {
        return operand;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        } else if (!super.equals(object)) {
            return false;
        }

        OperandInstance that = (OperandInstance) object;
        return operand.equals(that.operand);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + operand.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OperandInstance {" +
                "operand=" + operand +
                "} " + super.toString();
    }
}
