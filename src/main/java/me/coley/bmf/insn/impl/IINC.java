package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class IINC extends Instruction {
    public int index, increment;

    public IINC(int index, int increment) {
        super(OpType.VARIABLE, Instruction.IINC, 3);
        this.index = index;
        this.increment = increment;
    }
}
