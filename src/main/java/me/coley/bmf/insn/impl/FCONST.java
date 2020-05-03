package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;
import me.coley.bmf.insn.SingleValueInstruction;

public class FCONST extends SingleValueInstruction<Float> {
    public FCONST(float value) {
        super(OpType.CONST_VALUE, opFromValue((int) Math.round(value)), 1, value);
    }

    private static int opFromValue(int value) {
        if (value == 0)
            return Instruction.FCONST_0;
        if (value == 1)
            return Instruction.FCONST_1;
        else return Instruction.FCONST_2;
    }
}
