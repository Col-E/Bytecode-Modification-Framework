package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractLOAD;
import me.coley.bmf.insn.Instruction;

public class FLOAD extends AbstractLOAD {
    public FLOAD(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.FLOAD_0;
        else if (index == 1)
            return Instruction.FLOAD_1;
        else if (index == 2)
            return Instruction.FLOAD_2;
        else if (index == 3)
            return Instruction.FLOAD_3;
        else return Instruction.FLOAD;
    }
}
