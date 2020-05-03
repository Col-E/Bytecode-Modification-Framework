package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class WIDE extends Instruction {
    private final int nextModifier;

    public WIDE(int opcode, int length, int nextModifier) {
        super(OpType.OTHER, Instruction.WIDE, nextModifier);
        this.nextModifier = nextModifier;
    }

}
