package io.github.bmf.util;

import io.github.bmf.ClassNode;
import io.github.bmf.consts.ConstClass;
import io.github.bmf.consts.ConstUTF8;

public class ConstUtil {

    /**
     * Retrieves a string from the constant pool of a given ClassNode.
     * 
     * @param node
     * @param index
     * @return
     */
    public static String getUTF8String(ClassNode node, int index) {
        return ((ConstUTF8) node.getConst(index)).getValue();
    }

    /**
     * Retrieves the given node's name.
     * 
     * @param node
     * @return
     */
    public static String getName(ClassNode node) {
        return ((ConstUTF8) node.getConst(((ConstClass) node.getConst(node.classIndex)).getValue())).getValue();
    }

    /**
     * Retrieves the given node's super name.
     * 
     * @param node
     * @return
     */
    public static String getSuperName(ClassNode node) {
        return ((ConstUTF8) node.getConst(((ConstClass) node.getConst(node.superIndex)).getValue())).getValue();
    }

}
