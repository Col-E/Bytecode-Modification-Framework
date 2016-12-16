package io.github.bmf.opcode.impl;

import io.github.bmf.opcode.AbstractRETURN;
import io.github.bmf.opcode.Opcode;
import io.github.bmf.opcode.OpcodeType;
import io.github.bmf.type.Type;

public class IRETURN extends AbstractRETURN {
    public IRETURN() {
        super(OpcodeType.RETURN, Opcode.IRETURN, Type.INT);
    }
}
