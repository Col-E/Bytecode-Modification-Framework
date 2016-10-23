package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class LAND extends Opcode {
    public LAND() {
        super(OpcodeType.MATH, Opcode.LAND, 1);
    }
}
