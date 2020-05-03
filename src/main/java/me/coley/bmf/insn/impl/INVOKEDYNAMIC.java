package me.coley.bmf.insn.impl;

import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.OpType;

public class INVOKEDYNAMIC extends Instruction {
    public int declaringClass;
    public int indyIndex;

    public INVOKEDYNAMIC(int declaringClass) {
        super(OpType.STACK, Instruction.INVOKEDYNAMIC, 5);
        this.declaringClass = declaringClass;
    }

    // For writing:
    // byte - opcode
    // short - declaringClass
    // byte - 0
    // byte - 0

    // From javap, pretty sure InvokeDynamic index is based off their place in
    // the class file.
    // First instance = first Indy
    // Last instance = last Indy
}
