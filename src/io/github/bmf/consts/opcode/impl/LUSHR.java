package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LUSHR extends Opcode {
    public LUSHR() {
        super(OpcodeType.MATH, Opcode.LUSHR, 1);
    }
}
