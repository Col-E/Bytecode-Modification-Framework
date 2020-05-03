package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class DUP2 extends Instruction {
    public DUP2() {
        super(OpType.STACK, Instruction.DUP2, 1);
    }
}
