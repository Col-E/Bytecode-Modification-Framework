package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LXOR extends Instruction {
    public LXOR() {
        super(OpType.MATH, Instruction.LXOR, 1);
    }
}
