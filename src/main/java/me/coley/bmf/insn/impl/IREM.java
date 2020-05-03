package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IREM extends Instruction {
    public IREM() {
        super(OpType.MATH, Instruction.IREM, 1);
    }
}
