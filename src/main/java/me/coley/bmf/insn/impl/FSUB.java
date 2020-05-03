package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FSUB extends Instruction {
    public FSUB() {
        super(OpType.MATH, Instruction.FSUB, 1);
    }
}
