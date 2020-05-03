package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractMethodInstruction;
import me.coley.bmf.insn.Instruction;

public class INVOKEVIRTUAL extends AbstractMethodInstruction {
    public INVOKEVIRTUAL(int methodIndex) {
        super(Instruction.INVOKEVIRTUAL, 5, methodIndex);
    }
}
