package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractSTORE;
import me.coley.bmf.insn.Instruction;

public class LSTORE extends AbstractSTORE {
    public LSTORE(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.LSTORE_0;
        else if (index == 1)
            return Instruction.LSTORE_1;
        else if (index == 2)
            return Instruction.LSTORE_2;
        else if (index == 3)
            return Instruction.LSTORE_3;
        else return Instruction.LSTORE;
    }
}
