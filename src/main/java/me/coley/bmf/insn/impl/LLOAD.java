package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractLOAD;
import me.coley.bmf.insn.Instruction;

public class LLOAD extends AbstractLOAD {
    public LLOAD(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.LLOAD_0;
        else if (index == 1)
            return Instruction.LLOAD_1;
        else if (index == 2)
            return Instruction.LLOAD_2;
        else if (index == 3)
            return Instruction.LLOAD_3;
        else return Instruction.LLOAD;
    }
}
