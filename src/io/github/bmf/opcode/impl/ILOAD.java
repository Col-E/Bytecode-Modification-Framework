package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractLOAD;
import io.github.bmf.opcode.Opcode;

public class ILOAD extends AbstractLOAD {
    public ILOAD(int index) {
        super(opFromIndex(index), index);
    }

    private static int opFromIndex(int index) {
        if (index == 0)
            return Opcode.ILOAD_0;
        else if (index == 1)
            return Opcode.ILOAD_1;
        else if (index == 2)
            return Opcode.ILOAD_2;
        else if (index == 3)
            return Opcode.ILOAD_3;
        else return Opcode.ILOAD;
    }
}
