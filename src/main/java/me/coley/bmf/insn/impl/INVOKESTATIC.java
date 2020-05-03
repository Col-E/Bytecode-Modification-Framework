package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractMethodInstruction;
import me.coley.bmf.insn.Instruction;

public class INVOKESTATIC extends AbstractMethodInstruction {
    public INVOKESTATIC(int methodIndex) {
        super(Instruction.INVOKESTATIC, 5, methodIndex);
    }
}
