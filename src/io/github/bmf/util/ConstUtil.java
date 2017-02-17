package io.github.bmf.util;

import java.util.ArrayList;
import java.util.List;

import io.github.bmf.ClassNode;
import io.github.bmf.consts.ConstClass;
import io.github.bmf.consts.ConstUTF8;
import io.github.bmf.consts.Constant;
import io.github.bmf.consts.ConstantType;

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
        return getClassName(node, node.classIndex);
    }

    /**
     * Retrieves the given node's super name.
     * 
     * @param node
     * @return
     */
    public static String getSuperName(ClassNode node) {
        return getClassName(node, node.superIndex);
    }

    /**
     * Gets a name from a ConstClass at index i in a given node's constant pool.
     * 
     * @param node
     * @param classIndex
     * @return
     */
    public static String getClassName(ClassNode node, int classIndex) {
        return ((ConstUTF8) node.getConst(((ConstClass) node.getConst(classIndex)).getValue())).getValue();
    }

    /**
     * Returns a list of Constants from the given ClassNode of the given type.
     * 
     * @param cn
     * @param type
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T extends Constant> List<T> getConstants(ClassNode cn, ConstantType type) {
        List<Constant> list = new ArrayList<Constant>();
        for (Constant c : cn.constants) {
            if (c == null)
                continue;
            if (c.type == type)
                list.add(c);
        }
        return (List<T>) list;
    }
}
