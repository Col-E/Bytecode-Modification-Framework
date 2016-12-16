package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IFNONNULL extends AbstractJump {
    public IFNONNULL(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IFNONNULL, jumpIndex);
    }
}
