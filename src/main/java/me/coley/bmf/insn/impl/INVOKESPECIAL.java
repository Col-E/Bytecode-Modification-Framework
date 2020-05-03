package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractMethodInstruction;
import me.coley.bmf.insn.Instruction;

public class INVOKESPECIAL extends AbstractMethodInstruction {
    public INVOKESPECIAL(int methodIndex) {
        super(Instruction.INVOKESPECIAL, 5, methodIndex);
    }
}
