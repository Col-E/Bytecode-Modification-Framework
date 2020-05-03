package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IOR extends Instruction {
    public IOR() {
        super(OpType.MATH, Instruction.IOR, 1);
    }
}
