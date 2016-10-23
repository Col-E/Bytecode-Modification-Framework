package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;

public class ASTORE extends AbstractSTORE {
    public ASTORE(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0) return Opcode.ASTORE_0;
        else if (index == 1) return Opcode.ASTORE_1;
        else if (index == 2) return Opcode.ASTORE_2;
        else if (index == 3) return Opcode.ASTORE_3;
        else return Opcode.ASTORE;
    }
}
