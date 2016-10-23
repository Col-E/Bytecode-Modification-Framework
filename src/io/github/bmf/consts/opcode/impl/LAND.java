package io.github.bmf.consts.opcode.impl;

import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;

public class LAND extends Opcode {
    public LAND() {
        super(OpcodeType.MATH, Opcode.LAND, 1);
    }
}
