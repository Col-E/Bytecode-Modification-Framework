package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractSTORE;
import me.coley.bmf.insn.Instruction;

public class ASTORE extends AbstractSTORE {
    public ASTORE(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Instruction.ASTORE_0;
        else if (index == 1)
            return Instruction.ASTORE_1;
        else if (index == 2)
            return Instruction.ASTORE_2;
        else if (index == 3)
            return Instruction.ASTORE_3;
        else return Instruction.ASTORE;
    }
}
