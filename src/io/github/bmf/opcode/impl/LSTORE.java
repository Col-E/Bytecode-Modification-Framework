package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractSTORE;
import io.github.bmf.opcode.Opcode;

public class LSTORE extends AbstractSTORE {
    public LSTORE(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Opcode.LSTORE_0;
        else if (index == 1)
            return Opcode.LSTORE_1;
        else if (index == 2)
            return Opcode.LSTORE_2;
        else if (index == 3)
            return Opcode.LSTORE_3;
        else return Opcode.LSTORE;
    }
}
