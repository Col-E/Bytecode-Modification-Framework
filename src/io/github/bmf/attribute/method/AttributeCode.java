package io.github.bmf.attribute.method;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.attribute.AttributeType;

import java.util.List;

/**
 * The Code of a method. Contains sub-attributes:
 * <ul>
 * <li>{@link #lines LineNumberTable} - <i>May be null</i>
 * <li>{@link #variables LocalVariableTable} - <i>May be null</i>
 * <li>{@link #variableTypes LocalVariableTypeTable}
 * <li>{@link #stackMap StackMapTable}
 * </ul>
 * May contain others, but those four are standard.
 *
 * @author Matt
 */
public class AttributeCode extends Attribute {
    /**
     * Max number of values allowed on the stack.
     */
    public int stack;
    /**
     * Max number of variables allowed in the method.
     */
    public int locals;
    /**
     * A {@link io.github.bmf.attribute.method.AttributeLineNumberTable table}
     * that correlates opcode indices and line numbers <i>(Debugging). <br>
     * May be null.</i>
     */
    public AttributeLineNumberTable lines;
    /**
     * A {@link io.github.bmf.attribute.method.AttributeLocalVariableTable
     * table} containing local variables.
     */
    public AttributeLocalVariableTable variables;
    /**
     * A {@link io.github.bmf.attribute.method.AttributeLocalVariableTypeTable
     * table} containing types of local variables. <br>
     * <i>May be null</i>
     */
    public AttributeLocalVariableTypeTable variableTypes;
    /**
     * A {@link io.github.bmf.attribute.method.AttributeStackMapTable table}
     * that supplies information about the stack and stored locals at given
     * bytecode offsets.
     */
    public AttributeStackMapTable stackMap;

    /**
     * TEMPORARY??
     */
    public OpcodeListData_TEMP opcodes;

    public AttributeCode(int name, int stack, int locals, OpcodeListData_TEMP opcodes, List<Attribute> attributes) {
        super(name, AttributeType.CONSTANT_VALUE);
        this.stack = stack;
        this.locals = locals;
        this.opcodes = opcodes;
        for (Attribute attribute : attributes) {
            switch (attribute.type) {
            case LINE_NUMBER_TABLE:
                lines = (AttributeLineNumberTable) attribute;
                break;
            case LOCAL_VARIABLE_TABLE:
                variables = (AttributeLocalVariableTable) attribute;
                break;
            case LOCAL_VARIABLE_TYPE_TABLE:
                variableTypes = (AttributeLocalVariableTypeTable) attribute;
                break;
            case STACK_MAP_TABLE:
                stackMap = (AttributeStackMapTable) attribute;
                break;
            default:
                System.err.println("UNHANDLED CODE ATTRIB: " + attribute.type.name());
                break;
            }
        }
    }

    @Override
    public int getLength() {
        // u2: max_stack
        // u2: max_locals
        // u4: code_length
        // ??: CODE
        // u2: attributes_count
        // ??: ATTRIBS
        // TODO: Verify this is correct
        int len = BASE_LEN + 8;
        if (opcodes != null)
            len += opcodes.getLength();
        len += 2;
        if (variables != null)
            len += variables.getLength();
        if (variableTypes != null)
            len += variableTypes.getLength();
        if (stackMap != null)
            len += stackMap.getLength();
        return len;
    }

}
