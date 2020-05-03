package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractSTORE;
import me.coley.bmf.insn.Instruction;

public class ISTORE extends AbstractSTORE {
    public ISTORE(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.ISTORE_0;
        else if (index == 1)
            return Instruction.ISTORE_1;
        else if (index == 2)
            return Instruction.ISTORE_2;
        else if (index == 3)
            return Instruction.ISTORE_3;
        else return Instruction.ISTORE;
    }
}
