package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractLOAD;
import me.coley.bmf.insn.Instruction;

public class ILOAD extends AbstractLOAD {
    public ILOAD(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.ILOAD_0;
        else if (index == 1)
            return Instruction.ILOAD_1;
        else if (index == 2)
            return Instruction.ILOAD_2;
        else if (index == 3)
            return Instruction.ILOAD_3;
        else return Instruction.ILOAD;
    }
}
