package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class SWAP extends Opcode {
    public SWAP() {
        super(OpcodeType.STACK, Opcode.SWAP, 1);
    }
}
