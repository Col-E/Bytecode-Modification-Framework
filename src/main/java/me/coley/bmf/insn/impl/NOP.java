package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class NOP extends Instruction {
    public NOP() {
        super(OpType.OTHER, Instruction.NOP, 1);
    }
}
