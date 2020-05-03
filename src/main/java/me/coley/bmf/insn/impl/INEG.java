package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class INEG extends Instruction {
    public INEG() {
        super(OpType.MATH, Instruction.INEG, 1);
    }
}
