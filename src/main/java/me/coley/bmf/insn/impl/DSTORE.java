package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractSTORE;
import me.coley.bmf.insn.Instruction;

public class DSTORE extends AbstractSTORE {
    public DSTORE(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.DSTORE_0;
        else if (index == 1)
            return Instruction.DSTORE_1;
        else if (index == 2)
            return Instruction.DSTORE_2;
        else if (index == 3)
            return Instruction.DSTORE_3;
        else return Instruction.DSTORE;
    }
}
