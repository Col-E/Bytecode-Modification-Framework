package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FADD extends Instruction {
    public FADD() {
        super(OpType.MATH, Instruction.FADD, 1);
    }
}
