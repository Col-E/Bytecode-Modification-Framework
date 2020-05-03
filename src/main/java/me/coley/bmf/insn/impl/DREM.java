package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DREM extends Instruction {
    public DREM() {
        super(OpType.MATH, Instruction.DREM, 1);
    }
}
