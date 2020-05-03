package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class I2B extends Instruction {
    public I2B() {
        super(OpType.TYPE_CONVERSION, Instruction.I2B, 1);
    }
}
