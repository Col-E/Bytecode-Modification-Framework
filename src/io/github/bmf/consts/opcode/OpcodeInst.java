package io.github.bmf.consts.opcode;

import io.github.bmf.consts.opcode.impl.*;

public class OpcodeInst {

    public static final NOP NOP = new NOP();
    public static final ACONST_NULL ACONST_NULL = new ACONST_NULL();
    public static final ICONST ICONST_M1 = new ICONST(-1);
    public static final ICONST ICONST_0 = new ICONST(0);
    public static final ICONST ICONST_1 = new ICONST(1);
    public static final ICONST ICONST_2 = new ICONST(2);
    public static final ICONST ICONST_3 = new ICONST(3);
    public static final ICONST ICONST_4 = new ICONST(4);
    public static final ICONST ICONST_5 = new ICONST(5);
    public static final DCONST DCONST_0 = new DCONST(0);
    public static final DCONST DCONST_1 = new DCONST(1);
    public static final FCONST FCONST_0 = new FCONST(0);
    public static final FCONST FCONST_1 = new FCONST(1);
    public static final FCONST FCONST_2 = new FCONST(2);
    public static final LCONST LCONST_0 = new LCONST(0);
    public static final LCONST LCONST_1 = new LCONST(1);
    public static final ALOAD ALOAD_0 = new ALOAD(0);
    public static final ALOAD ALOAD_1 = new ALOAD(1);
    public static final ALOAD ALOAD_2 = new ALOAD(2);
    public static final ALOAD ALOAD_3 = new ALOAD(3);
    public static final ILOAD ILOAD_0 = new ILOAD(0);
    public static final ILOAD ILOAD_1 = new ILOAD(1);
    public static final ILOAD ILOAD_2 = new ILOAD(2);
    public static final ILOAD ILOAD_3 = new ILOAD(3);
    public static final FLOAD FLOAD_0 = new FLOAD(0);
    public static final FLOAD FLOAD_1 = new FLOAD(1);
    public static final FLOAD FLOAD_2 = new FLOAD(2);
    public static final FLOAD FLOAD_3 = new FLOAD(3);
    public static final DLOAD DLOAD_0 = new DLOAD(0);
    public static final DLOAD DLOAD_1 = new DLOAD(1);
    public static final DLOAD DLOAD_2 = new DLOAD(2);
    public static final DLOAD DLOAD_3 = new DLOAD(3);
    public static final LLOAD LLOAD_0 = new LLOAD(0);
    public static final LLOAD LLOAD_1 = new LLOAD(1);
    public static final LLOAD LLOAD_2 = new LLOAD(2);
    public static final LLOAD LLOAD_3 = new LLOAD(3);
}
