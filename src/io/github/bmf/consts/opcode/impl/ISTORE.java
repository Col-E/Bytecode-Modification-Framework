package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;

public class ISTORE extends AbstractSTORE {
    public ISTORE(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0) return Opcode.ISTORE_0;
        else if (index == 1) return Opcode.ISTORE_1;
        else if (index == 2) return Opcode.ISTORE_2;
        else if (index == 3) return Opcode.ISTORE_3;
        else return Opcode.ISTORE;
    }
}
