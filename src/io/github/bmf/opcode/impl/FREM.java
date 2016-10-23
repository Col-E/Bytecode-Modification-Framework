package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class FREM extends Opcode {
    public FREM() {
        super(OpcodeType.MATH, Opcode.FREM, 1);
    }
}
