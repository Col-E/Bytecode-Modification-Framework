package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class ISHR extends Opcode {
    public ISHR() {
        super(OpcodeType.MATH, Opcode.ISHR, 1);
    }
}
