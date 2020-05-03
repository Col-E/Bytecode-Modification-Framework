package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;
import me.coley.bmf.insn.SingleValueInstruction;

public class ICONST extends SingleValueInstruction<Integer> {
    public ICONST(int value) {
        super(OpType.CONST_VALUE, opFromValue(value), 1, value);
    }

    private static int opFromValue(int value) {
        if (value == 0)
            return Instruction.ICONST_0;
        else if (value == 1)
            return Instruction.ICONST_1;
        else if (value == 2)
            return Instruction.ICONST_2;
        else if (value == 3)
            return Instruction.ICONST_3;
        else if (value == 4)
            return Instruction.ICONST_4;
        else return Instruction.ICONST_5;
    }
}
