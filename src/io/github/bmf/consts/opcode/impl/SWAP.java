package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class SWAP extends Opcode {
    public SWAP() {
        super(OpcodeType.STACK, Opcode.SWAP, 1);
    }
}
