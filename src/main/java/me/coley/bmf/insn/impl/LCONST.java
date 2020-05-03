package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;
import me.coley.bmf.insn.SingleValueInstruction;

public class LCONST extends SingleValueInstruction<Long> {
    public LCONST(long value) {
        super(OpType.CONST_VALUE, opFromValue((int) value), 1, value);
    }

    private static int opFromValue(int value) {
        if (value == 0)
            return Instruction.LCONST_0;
        else return Instruction.LCONST_1;
    }
}
