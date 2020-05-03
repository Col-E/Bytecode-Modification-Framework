package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.AbstractMethodInstruction;
import me.coley.bmf.insn.Instruction;

public class INVOKEINTERFACE extends AbstractMethodInstruction {
    public int argWords;

    public INVOKEINTERFACE(int methodIndex, int argWords, int zero) {
        super(Instruction.INVOKEINTERFACE, 7, methodIndex);
        this.argWords = argWords;
    }
}
