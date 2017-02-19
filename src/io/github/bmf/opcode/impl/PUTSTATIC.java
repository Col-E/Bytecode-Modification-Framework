package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractFieldOpcode;
import io.github.bmf.opcode.Opcode;

public class PUTSTATIC extends AbstractFieldOpcode {
    public PUTSTATIC( int fieldIndex) {
        super(Opcode.PUTSTATIC, fieldIndex);
    }
}
