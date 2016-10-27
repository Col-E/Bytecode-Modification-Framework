package io.github.bmf.type;

import io.github.bmf.util.Box;

public abstract class Type {


    public static final Primitive BYTE = new Primitive(Sort.BYTE, "B");
    public static final Primitive CHAR = new Primitive(Sort.CHAR, "C");
    public static final Primitive DOUBLE = new Primitive(Sort.DOUBLE, "D");
    public static final Primitive FLOAT = new Primitive(Sort.FLOAT, "F");
    public static final Primitive INT = new Primitive(Sort.INT, "I");
    public static final Primitive LONG = new Primitive(Sort.LONG, "J");
    public static final Primitive SHORT = new Primitive(Sort.SHORT, "S");
    public static final Primitive BOOLEAN = new Primitive(Sort.BOOLEAN, "Z");
    public static final Primitive VOID = new Primitive(Sort.VOID, "V");
    
    public final int sort;

    public Type(int sort) {
        this.sort = sort;
    }

    abstract public String toString();

    public static class Primitive extends Type {

        public final String desc;

        private Primitive(int type, String desc) {
            super(type);
            this.desc = desc;
        }

        public String toString() {
            return desc;
        }

    }

    public static class Class extends Type {

        public final Box<String> className;

        public Class(Box<String> className) {
            super(Sort.CLASS);
            this.className = className;
        }

        @Override
        public String toString() {
            return "L" + className.value + ";";
        }
    }

    public class Array extends Type {

        public final Type itemType;

        public Array(Type itemType) {
            super(Sort.ARRAY);
            this.itemType = itemType;
        }

        public int getDepth() {
            int i = 1;
            Type next = itemType;
            while (next.sort == Sort.ARRAY) {
                next = ((Array) next).itemType;
                i++;
            }
            return i;
        }

        @Override
        public String toString() {
            return "[" + itemType.toString();
        }
    }

}
