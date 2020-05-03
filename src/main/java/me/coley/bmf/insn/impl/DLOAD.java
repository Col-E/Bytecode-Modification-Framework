package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractLOAD;
import me.coley.bmf.insn.Instruction;

public class DLOAD extends AbstractLOAD {
    public DLOAD(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.DLOAD_0;
        else if (index == 1)
            return Instruction.DLOAD_1;
        else if (index == 2)
            return Instruction.DLOAD_2;
        else if (index == 3)
            return Instruction.DLOAD_3;
        else return Instruction.DLOAD;
    }
}
