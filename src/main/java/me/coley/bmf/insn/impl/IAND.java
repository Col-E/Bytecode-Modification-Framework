package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IAND extends Instruction {
    public IAND() {
        super(OpType.MATH, Instruction.IAND, 1);
    }
}
