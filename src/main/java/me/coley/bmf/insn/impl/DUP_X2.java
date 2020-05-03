package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DUP_X2 extends Instruction {
    public DUP_X2() {
        super(OpType.STACK, Instruction.DUP_X2, 1);
    }
}
