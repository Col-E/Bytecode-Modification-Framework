package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class LCMP extends Instruction {
    public LCMP() {
        super(OpType.STACK, Instruction.LCMP, 1);
    }
}
