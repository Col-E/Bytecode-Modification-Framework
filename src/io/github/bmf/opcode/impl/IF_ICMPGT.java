package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractJump;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;

public class IF_ICMPGT extends AbstractJump {
    public IF_ICMPGT(int jumpIndex) {
        super(OpcodeType.ARRAY, Opcode.IF_ICMPGT, jumpIndex);
    }
}
