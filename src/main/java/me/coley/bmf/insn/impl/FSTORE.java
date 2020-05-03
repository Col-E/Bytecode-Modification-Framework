package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractSTORE;
import me.coley.bmf.insn.Instruction;

public class FSTORE extends AbstractSTORE {
    public FSTORE(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.FSTORE_0;
        else if (index == 1)
            return Instruction.FSTORE_1;
        else if (index == 2)
            return Instruction.FSTORE_2;
        else if (index == 3)
            return Instruction.FSTORE_3;
        else return Instruction.FSTORE;
    }
}
