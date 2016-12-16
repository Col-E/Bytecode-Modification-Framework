package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IFLT extends AbstractJump {
    public IFLT(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IFLT, jumpIndex);
    }
}
