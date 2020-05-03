package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class POP2 extends Instruction {
    public POP2() {
        super(OpType.STACK, Instruction.POP2, 1);
    }
}
