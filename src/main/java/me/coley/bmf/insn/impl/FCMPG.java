package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class FCMPG extends Instruction {
    public FCMPG() {
        super(OpType.STACK, Instruction.FCMPG, 1);
    }
}
