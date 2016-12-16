package io.github.bmf.opcode;

import io.github.bmf.type.Type;

public class AbstractRETURN extends Opcode {
    public final Type returnType;

    public AbstractRETURN(OpcodeType optype, int opcode, Type returnType) {
        super(optype, opcode, 1);
        this.returnType = returnType;
    }
}
