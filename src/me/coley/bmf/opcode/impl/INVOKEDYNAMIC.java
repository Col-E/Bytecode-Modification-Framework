package me.coley.bmf.opcode.impl;

import me.coley.bmf.opcode.Opcode;
import me.coley.bmf.opcode.OpcodeType;

public class INVOKEDYNAMIC extends Opcode {
    public int declaringClass;
    public int indyIndex;

    public INVOKEDYNAMIC(int declaringClass, int indyIndex) {
        super(OpcodeType.STACK, Opcode.INVOKEDYNAMIC, 5);
        this.declaringClass = declaringClass;
        this.indyIndex = indyIndex;
    }
}
