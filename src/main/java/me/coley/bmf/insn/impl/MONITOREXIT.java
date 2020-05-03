package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class MONITOREXIT extends Instruction {
    public MONITOREXIT() {
        super(OpType.OTHER, Instruction.MONITOREXIT, 1);
    }
}
