package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class I2F extends Instruction {
    public I2F() {
        super(OpType.TYPE_CONVERSION, Instruction.I2F, 1);
    }
}
