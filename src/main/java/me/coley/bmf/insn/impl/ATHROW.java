package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class ATHROW extends Instruction {
    public ATHROW() {
        super(OpType.ARRAY, Instruction.ATHROW, 1);
    }
}
