/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.mipsasm.assembler;

import me.zhanghai.mipsasm.util.BitArray;

public class Target implements Operand, AssemblyProvider {

    private boolean isLabel;

    private TargetLabel label;

    private InstructionIndex instructionIndex;

    public Target(boolean isLabel, TargetLabel label, InstructionIndex instructionIndex) {
        this.isLabel = isLabel;
        this.label = label;
        this.instructionIndex = instructionIndex;
    }

    public static Target of(TargetLabel label) {
        return new Target(true, label, null);
    }

    public static Target of(InstructionIndex instructionIndex) {
        return new Target(false, null, instructionIndex);
    }

    public boolean isLabel() {
        return isLabel;
    }

    public TargetLabel getLabel() {
        if (!isLabel) {
            throw new IllegalStateException("Not a label: " + instructionIndex);
        }
        return label;
    }

    public InstructionIndex getInstructionIndex() {
        if (isLabel) {
            throw new IllegalStateException("Not an instruction index: " + label);
        }
        return instructionIndex;
    }

    @Override
    public BitArray assemble(AssemblyContext context) throws AssemblerException {
        if (isLabel) {
            return label.assemble(context);
        } else {
            return instructionIndex.assemble(context);
        }
    }

    @Override
    public String toString() {
        return isLabel ? label.toString() : instructionIndex.toString();
    }
}
