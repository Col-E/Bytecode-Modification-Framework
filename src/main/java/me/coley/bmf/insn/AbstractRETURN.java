package me.coley.bmf.insn;

import me.coley.bmf.type.Type;

public class AbstractRETURN extends Instruction {
    public final Type returnType;

    public AbstractRETURN(OpType optype, int opcode, Type returnType) {
        super(optype, opcode, 1);
        this.returnType = returnType;
    }
}
