package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LUSHR extends Opcode {
    public LUSHR() {
        super(OpcodeType.MATH, Opcode.LUSHR, 1);
    }
}
