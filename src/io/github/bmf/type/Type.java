package io.github.bmf.type;

import java.util.ArrayList;
import java.util.List;

import io.github.bmf.util.descriptors.MethodDescriptor;
import io.github.bmf.util.mapping.Mapping;

public abstract class Type {
    public static final PrimitiveType BYTE = new PrimitiveType(Sort.BYTE, "B");
    public static final PrimitiveType CHAR = new PrimitiveType(Sort.CHAR, "C");
    public static final PrimitiveType DOUBLE = new PrimitiveType(Sort.DOUBLE, "D");
    public static final PrimitiveType FLOAT = new PrimitiveType(Sort.FLOAT, "F");
    public static final PrimitiveType INT = new PrimitiveType(Sort.INT, "I");
    public static final PrimitiveType LONG = new PrimitiveType(Sort.LONG, "J");
    public static final PrimitiveType SHORT = new PrimitiveType(Sort.SHORT, "S");
    public static final PrimitiveType BOOLEAN = new PrimitiveType(Sort.BOOLEAN, "Z");
    public static final PrimitiveType VOID = new PrimitiveType(Sort.VOID, "V");
    public final int sort;

    public Type(int sort) {
        this.sort = sort;
    }

    abstract public String toDesc();

    /**
     * This is all really ugly but trying to keep
     * "change once, applies everwhere" logic in kinda makes pretty code hard.
     * 
     * @param mapping
     * @param desc
     * @return
     */
    public static MethodDescriptor method(Mapping mapping, String desc) {
        char[] carr = desc.toCharArray();
        List<Type> types = new ArrayList<Type>();
        System.out.println(desc);
        int i = 1;
        while (true) {
            char c = carr[i];
            if (c == ')') break;
            if (c == '[') {
                ArrayType array = readArray(mapping, desc, i + 1);
                types.add(array);
                i += array.toDesc().length() - 1;
            } else if (c == 'L') {
                int len = 1;
                while (c != ';') {
                    c = carr[i + len];
                    len++;
                }
                types.add(type(mapping, desc, i, len));
                i += len - 1;
            } else {
                Type prim = readPrim(c);
                if (prim != null) {
                    types.add(prim);
                } else {
                    throw new RuntimeException("Invalid method descriptor[" + i + ":" + c + "]: " + desc);
                }
            }
            i++;
        }
        for (Type t : types) {
            System.out.println("\t" + t.toDesc());
        }
        return new MethodDescriptor(types, type(mapping, desc.substring(desc.indexOf(')') + 1)));
    }

    private static ArrayType readArray(Mapping mapping, String desc, int i) {
        char[] carr = desc.toCharArray();
        Type t = null;
        switch (carr[i]) {
        case '[':
            t = readArray(mapping, desc, i + 1);
            break;
        case 'L':
            int len = 1;
            char c = carr[i];
            while (c != ';') {
                c = carr[i + len];
                len++;
            }
            t = type(mapping, desc, i, len);
            i += len - 1;
            break;
        }
        if (t == null) t = readPrim(carr[i]);
        return new ArrayType(t);
    }

    private static Type readPrim(char c) {
        switch (c) {
        case 'B':
            return BYTE;
        case 'C':
            return CHAR;
        case 'D':
            return DOUBLE;
        case 'F':
            return FLOAT;
        case 'I':
            return INT;
        case 'J':
            return LONG;
        case 'S':
            return SHORT;
        case 'Z':
            return BOOLEAN;
        case 'V':
            return VOID;
        default:
            break;
        }
        return null;
    }

    private static Type type(Mapping mapping, String desc) {
        if (desc.length() == 1) {
            return readPrim(desc.charAt(0));
        } else if (desc.charAt(0) == '[') { return readArray(mapping, desc, 1); }
        return new ClassType(mapping.get(desc));
    }

    private static Type type(Mapping mapping, String desc, int i, int len) {
        return new ClassType(mapping.get(desc.substring(i + 1, i + len - 1)));
    }
}
