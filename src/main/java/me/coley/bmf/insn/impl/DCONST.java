package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;
import me.coley.bmf.insn.SingleValueInstruction;

public class DCONST extends SingleValueInstruction<Double> {
    public DCONST(double value) {
        super(OpType.CONST_VALUE, opFromValue((int) Math.round(value)), 1, value);
    }

    private static int opFromValue(int value) {
        if (value == 0)
            return Instruction.DCONST_0;
        else return Instruction.DCONST_1;
    }
}
