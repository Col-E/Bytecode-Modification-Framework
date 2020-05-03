package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IADD extends Instruction {
    public IADD() {
        super(OpType.MATH, Instruction.IADD, 1);
    }
}
