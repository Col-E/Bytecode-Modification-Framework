package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class I2C extends Instruction {
    public I2C() {
        super(OpType.TYPE_CONVERSION, Instruction.I2C, 1);
    }
}
