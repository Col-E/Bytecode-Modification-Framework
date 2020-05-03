package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractLOAD;
import me.coley.bmf.insn.Instruction;

public class ALOAD extends AbstractLOAD {
    public ALOAD(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.ALOAD_0;
        else if (index == 1)
            return Instruction.ALOAD_1;
        else if (index == 2)
            return Instruction.ALOAD_2;
        else if (index == 3)
            return Instruction.ALOAD_3;
        else return Instruction.ALOAD;
    }
}
