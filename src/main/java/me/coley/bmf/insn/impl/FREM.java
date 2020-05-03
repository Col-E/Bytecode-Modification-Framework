package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FREM extends Instruction {
    public FREM() {
        super(OpType.MATH, Instruction.FREM, 1);
    }
}
