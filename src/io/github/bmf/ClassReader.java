package io.github.bmf;

import com.google.common.collect.Lists;
import io.github.bmf.attribute.*;
import io.github.bmf.attribute.annotation.*;
import io.github.bmf.attribute.annotation.element.*;
import io.github.bmf.attribute.clazz.*;
import io.github.bmf.attribute.field.AttributeConstantValue;
import io.github.bmf.attribute.method.*;
import io.github.bmf.consts.*;
import io.github.bmf.consts.opcode.Opcode;
import io.github.bmf.consts.opcode.OpcodeType;
import io.github.bmf.exception.InvalidClassException;
import io.github.bmf.util.io.StreamUtil;

/**
 * 
 * @author Matt
 *
 */
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("rawtypes")
public class ClassReader {
    public static ClassNode getNode(byte[] data) throws InvalidClassException, IOException {
        DataInputStream is = StreamUtil.fromBytes(data);
        if (is.readInt() != 0xCAFEBABE) { throw new InvalidClassException(); }
        // Create the node
        ClassNode node = new ClassNode();
        // Read version information
        node.minor = is.readUnsignedShort();
        node.major = is.readUnsignedShort();
        // Read the constant pool
        int poolIndex = 1;
        int poolLength = is.readUnsignedShort();
        node.setPoolSize(poolLength);
        while (poolIndex < poolLength) {
            ConstantType type = ConstantType.fromTag(is.readUnsignedByte());
            Constant constant = readConst(type, is);
            node.addConst(constant);
            if (type == ConstantType.DOUBLE || type == ConstantType.LONG) {
                // They take up 2 constant places in the pool.
                node.addConst(null);
                poolIndex++;
            }
            poolIndex++;
        }
        // Read access and index information
        node.access = is.readUnsignedShort();
        node.classIndex = is.readUnsignedShort();
        node.superIndex = is.readUnsignedShort();
        // Read interfaces
        int interfaceLength = is.readUnsignedShort();
        node.setInterfaceCount(interfaceLength);
        for (int i = 0; i < interfaceLength; i++) {
            node.addInterfaceIndex(is.readUnsignedShort());
        }
        // Read fields
        int fieldLength = is.readUnsignedShort();
        node.setFieldCount(fieldLength);
        for (int i = 0; i < fieldLength; i++) {
            FieldNode field = readField(node, is);
            node.addField(field);
        }
        // Read methods
        int methodLength = is.readUnsignedShort();
        node.setMethodCount(methodLength);
        for (int i = 0; i < methodLength; i++) {
            MethodNode method = readMethod(node, is);
            node.addMethod(method);
        }
        // Read class attributes
        int attribsLength = is.readUnsignedShort();
        for (int i = 0; i < attribsLength; i++) {
            Attribute attribute = readAttribute(node, is);
            node.addAttribute(attribute);
        }
        return node;
    }

    private static FieldNode readField(ClassNode owner, DataInputStream is) throws IOException {
        FieldNode field = new FieldNode(owner);
        field.access = is.readUnsignedShort();
        field.name = is.readUnsignedShort();
        field.desc = is.readUnsignedShort();
        int attribsLength = is.readUnsignedShort();
        for (int i = 0; i < attribsLength; i++) {
            Attribute attribute = readAttribute(owner, is);
            field.addAttribute(attribute);
        }
        return field;
    }

    private static MethodNode readMethod(ClassNode owner, DataInputStream is) throws IOException {
        MethodNode method = new MethodNode(owner);
        method.access = is.readUnsignedShort();
        method.name = is.readUnsignedShort();
        method.desc = is.readUnsignedShort();
        int attribsLength = is.readUnsignedShort();
        for (int i = 0; i < attribsLength; i++) {
            Attribute attribute = readAttribute(owner, is);
            method.addAttribute(attribute);
        }
        return method;
    }

    private static Attribute readAttribute(ClassNode owner, DataInputStream is) throws IOException {
        int nameIndex = is.readUnsignedShort();
        int length = is.readInt();
        String name = owner.getConst(nameIndex).value.toString();
        AttributeType attributeType = AttributeType.fromName(name);
        switch (attributeType) {
        case ANNOTATION_DEFAULT: {
            return new AttributeAnnotationDefault(nameIndex, readElementValue(owner, is));
        }
        case BOOTSTRAP_METHODS: {
            int methods = is.readUnsignedShort();
            List<BootstrapMethod> bsMethods = new ArrayList<BootstrapMethod>(methods);
            for (int i = 0; i < methods; i++) {
                int methodRef = is.readUnsignedShort();
                int args = is.readUnsignedShort();
                BootstrapMethod bsm = new BootstrapMethod(methodRef, args);
                for (int a = 0; a < args; a++) {
                    bsm.addArgument(is.readUnsignedShort());
                }
                bsMethods.set(i, bsm);
            }
            return new AttributeBootstrapMethods(nameIndex, bsMethods);
        }
        case CODE: {
            int maxStack = is.readUnsignedShort();
            int maxLocals = is.readUnsignedShort();
            MethodCode codeData = readMethodCode(owner, is);
            List<MethodException> exceptions = Lists.newArrayList();
            int exceptionLength = is.readUnsignedShort();
            for (int i = 0; i < exceptionLength; i++) {
                MethodException mexeption = new MethodException();
                int rangeStart = is.readUnsignedShort();
                int rangeEnd = is.readUnsignedShort();
                int rangeHandler = is.readUnsignedShort();
                int catchType = is.readUnsignedShort();
                mexeption.start = rangeStart;
                mexeption.end = rangeEnd;
                mexeption.handler = rangeHandler;
                mexeption.type = catchType;
                exceptions.add(mexeption);
            }
            List<Attribute> attributes = Lists.newArrayList();
            int attributeLength = is.readUnsignedShort();
            for (int i = 0; i < attributeLength; i++) {
                Attribute attribute = readAttribute(owner, is);
                attributes.add(attribute);
            }
            return new AttributeCode(nameIndex, maxStack, maxLocals, exceptions, codeData, attributes);
        }
        case CONSTANT_VALUE: {
            int value = is.readUnsignedShort();
            return new AttributeConstantValue(nameIndex, value);
        }
        case DEPRECATED: {
            return new AttributeDeprecated(nameIndex);
        }
        case ENCLOSING_METHOD: {
            int classIndex = is.readUnsignedShort();
            int methodIndex = is.readUnsignedShort();
            return new AttributeEnclosingMethod(nameIndex, classIndex, methodIndex);
        }
        case EXCEPTIONS: {
            int exceptionCount = is.readUnsignedShort();
            List<Integer> exceptionIndices = Lists.newArrayList();
            for (int i = 0; i < exceptionCount; i++) {
                exceptionIndices.add(is.readUnsignedShort());
            }
            return new AttributeExceptions(nameIndex, exceptionIndices);
        }
        case INNER_CLASSES: {
            int classCount = is.readUnsignedShort();
            List<InnerClass> classes = Lists.newArrayList();
            for (int i = 0; i < classCount; i++) {
                int innerIndex = is.readUnsignedShort();
                int outerIndex = is.readUnsignedShort();
                int cInnerName = is.readUnsignedShort();
                int innerAccess = is.readUnsignedShort();
                classes.add(new InnerClass(innerIndex, outerIndex, cInnerName, innerAccess));
            }
            return new AttributeInnerClasses(nameIndex, classes);
        }
        case LINE_NUMBER_TABLE: {
            LineNumberTable table = new LineNumberTable();
            int tableLength = is.readUnsignedShort();
            for (int i = 0; i < tableLength; i++) {
                int startPC = is.readUnsignedShort();
                int lineNumber = is.readUnsignedShort();
                table.add(startPC, lineNumber);
            }
            return new AttributeLineNumberTable(nameIndex, table);
        }
        case LOCAL_VARIABLE_TABLE: {
            int tableLength = is.readUnsignedShort();
            LocalVariableTable locals = new LocalVariableTable(tableLength);
            for (int i = 0; i < tableLength; i++) {
                int lstartPC = is.readUnsignedShort();
                int llength = is.readUnsignedShort();
                int lname = is.readUnsignedShort();
                int ldesc = is.readUnsignedShort();
                int lindex = is.readUnsignedShort();
                locals.add(lstartPC, llength, lname, ldesc, lindex);
            }
            return new AttributeLocalVariableTable(nameIndex, locals);
        }
        case LOCAL_VARIABLE_TYPE_TABLE: {
            int tableLength = is.readUnsignedShort();
            List<LocalVariableType> localTypes = Lists.newArrayList();
            for (int i = 0; i < tableLength; i++) {
                int lStart = is.readUnsignedShort();
                int lLen = is.readUnsignedShort();
                int lName = is.readUnsignedShort();
                int lSignature = is.readUnsignedShort();
                int lIndex = is.readUnsignedShort();
                localTypes.add(new LocalVariableType(lStart, lLen, lName, lSignature, lIndex));
            }
            return new AttributeLocalVariableTypeTable(nameIndex, localTypes);
        }

        case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
        case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
        case RUNTIME_VISIBLE_ANNOTATIONS:
        case RUNTIME_INVISIBLE_ANNOTATIONS: {
            return readAnnotations(owner, attributeType, is, nameIndex, length);
        }
        case SIGNATURE: {
            int sig = is.readUnsignedShort();
            return new AttributeSignature(nameIndex, sig);
        }
        case SOURCE_DEBUG_EXTENSION: {
            List<Integer> data = Lists.newArrayList();
            for (int i = 0; i < length; i++) {
                data.add(is.readUnsignedByte());
            }
            return new AttributeSourceDebugExtension(nameIndex, data);
        }
        case SOURCE_FILE: {
            int source = is.readUnsignedShort();
            return new AttributeSourceFile(nameIndex, source);
        }
        case STACK_MAP_TABLE: {
            // TODO: Do this
            byte[] data = new byte[length];
            is.read(data);
            return new AttributeStackMapTable(nameIndex, data);
        }
        case SYNTHETIC: {
            return new AttributeSynthetic(nameIndex);
        }
        default:
            break;
        }
        throw new RuntimeException("Unhandled attribute! " + attributeType);
    }

    private static MethodCode readMethodCode(ClassNode owner, DataInputStream is) throws IOException {
        int codeLength = is.readInt();
        // Store original opcode bytes
        byte[] origOpcodeBytes = new byte[codeLength];
        is.read(origOpcodeBytes);
        MethodCode codeData = new MethodCode(origOpcodeBytes);

        // Create opcode data
        codeData.opcodes = new ArrayList<Opcode>();
        DataInputStream opstr = StreamUtil.fromBytes(codeData.original);
        // Is there a way to find the # of opcodes? Can't seem to figure one out
        // aside from reading and knowing after the fact.
        while (opstr.available() > 0) {
            codeData.opcodes.add(readOpcode(opstr));
        }
        opstr.close();
        return codeData;
    }

    private static Opcode readOpcode(DataInputStream is) throws IOException {
        int code = is.readUnsignedByte();
        boolean wide = code == Opcode.WIDE;
        if (wide) {
            code = is.readUnsignedByte();
        }
        OpcodeType type = OpcodeType.fromValue(code);
        // Should opcodes representing constant values be constants themselves?
        // Ex: (general idea)
        // ICONST_0 --> Opcode.INST_ICONST_0
        //
        // Also is a BCEL-like setup for opcodes the best course of action?
        // Currently just have empty classes for each but no implementations.
        switch (type) {
        case AALOAD:
            break;
        case AASTORE:
            break;
        case ACONST_NULL:
            break;
        case ALOAD:
            break;
        case ALOAD_0:
            break;
        case ALOAD_1:
            break;
        case ALOAD_2:
            break;
        case ALOAD_3:
            break;
        case ANEWARRAY:
            break;
        case ARETURN:
            break;
        case ARRAYLENGTH:
            break;
        case ASTORE:
            break;
        case ASTORE_0:
            break;
        case ASTORE_1:
            break;
        case ASTORE_2:
            break;
        case ASTORE_3:
            break;
        case ATHROW:
            break;
        case BALOAD:
            break;
        case BASTORE:
            break;
        case BIPUSH:
            break;
        case BREAKPOINT:
            break;
        case CALOAD:
            break;
        case CASTORE:
            break;
        case CHECKCAST:
            break;
        case D2F:
            break;
        case D2I:
            break;
        case D2L:
            break;
        case DADD:
            break;
        case DALOAD:
            break;
        case DASTORE:
            break;
        case DCMPG:
            break;
        case DCMPL:
            break;
        case DCONST_0:
            break;
        case DCONST_1:
            break;
        case DDIV:
            break;
        case DLOAD:
            break;
        case DLOAD_0:
            break;
        case DLOAD_1:
            break;
        case DLOAD_2:
            break;
        case DLOAD_3:
            break;
        case DMUL:
            break;
        case DNEG:
            break;
        case DREM:
            break;
        case DRETURN:
            break;
        case DSTORE:
            break;
        case DSTORE_0:
            break;
        case DSTORE_1:
            break;
        case DSTORE_2:
            break;
        case DSTORE_3:
            break;
        case DSUB:
            break;
        case DUP:
            break;
        case DUP2:
            break;
        case DUP2_X1:
            break;
        case DUP2_X2:
            break;
        case DUP_X1:
            break;
        case DUP_X2:
            break;
        case F2D:
            break;
        case F2I:
            break;
        case F2L:
            break;
        case FADD:
            break;
        case FALOAD:
            break;
        case FASTORE:
            break;
        case FCMPG:
            break;
        case FCMPL:
            break;
        case FCONST_0:
            break;
        case FCONST_1:
            break;
        case FCONST_2:
            break;
        case FDIV:
            break;
        case FLOAD:
            break;
        case FLOAD_0:
            break;
        case FLOAD_1:
            break;
        case FLOAD_2:
            break;
        case FLOAD_3:
            break;
        case FMUL:
            break;
        case FNEG:
            break;
        case FREM:
            break;
        case FRETURN:
            break;
        case FSTORE:
            break;
        case FSTORE_0:
            break;
        case FSTORE_1:
            break;
        case FSTORE_2:
            break;
        case FSTORE_3:
            break;
        case FSUB:
            break;
        case GETFIELD:
            break;
        case GETSTATIC:
            break;
        case GOTO:
            break;
        case GOTO_W:
            break;
        case I2B:
            break;
        case I2C:
            break;
        case I2D:
            break;
        case I2F:
            break;
        case I2L:
            break;
        case I2S:
            break;
        case IADD:
            break;
        case IALOAD:
            break;
        case IAND:
            break;
        case IASTORE:
            break;
        case ICONST_0:
            break;
        case ICONST_1:
            break;
        case ICONST_2:
            break;
        case ICONST_3:
            break;
        case ICONST_4:
            break;
        case ICONST_5:
            break;
        case ICONST_M1:
            break;
        case IDIV:
            break;
        case IFEQ:
            break;
        case IFGE:
            break;
        case IFGT:
            break;
        case IFLE:
            break;
        case IFLT:
            break;
        case IFNE:
            break;
        case IFNONNULL:
            break;
        case IFNULL:
            break;
        case IF_ACMPEQ:
            break;
        case IF_ACMPNE:
            break;
        case IF_ICMPEQ:
            break;
        case IF_ICMPGE:
            break;
        case IF_ICMPGT:
            break;
        case IF_ICMPLE:
            break;
        case IF_ICMPLT:
            break;
        case IF_ICMPNE:
            break;
        case IINC:
            break;
        case ILOAD:
            break;
        case ILOAD_0:
            break;
        case ILOAD_1:
            break;
        case ILOAD_2:
            break;
        case ILOAD_3:
            break;
        case IMPDEP1:
            break;
        case IMPDEP2:
            break;
        case IMUL:
            break;
        case INEG:
            break;
        case INSTANCEOF:
            break;
        case INVOKEINTERFACE:
            break;
        case INVOKESPECIAL:
            break;
        case INVOKESTATIC:
            break;
        case INVOKEVIRTUAL:
            break;
        case IOR:
            break;
        case IREM:
            break;
        case IRETURN:
            break;
        case ISHL:
            break;
        case ISHR:
            break;
        case ISTORE:
            break;
        case ISTORE_0:
            break;
        case ISTORE_1:
            break;
        case ISTORE_2:
            break;
        case ISTORE_3:
            break;
        case ISUB:
            break;
        case IUSHR:
            break;
        case IXOR:
            break;
        case JSR:
            break;
        case JSR_W:
            break;
        case L2D:
            break;
        case L2F:
            break;
        case L2I:
            break;
        case LADD:
            break;
        case LALOAD:
            break;
        case LAND:
            break;
        case LASTORE:
            break;
        case LCMP:
            break;
        case LCONST_0:
            break;
        case LCONST_1:
            break;
        case LDC:
            break;
        case LDC2_W:
            break;
        case LDC_W:
            break;
        case LDIV:
            break;
        case LLOAD:
            break;
        case LLOAD_0:
            break;
        case LLOAD_1:
            break;
        case LLOAD_2:
            break;
        case LLOAD_3:
            break;
        case LMUL:
            break;
        case LNEG:
            break;
        case LOOKUPSWITCH:
            break;
        case LOR:
            break;
        case LREM:
            break;
        case LRETURN:
            break;
        case LSHL:
            break;
        case LSHR:
            break;
        case LSTORE:
            break;
        case LSTORE_0:
            break;
        case LSTORE_1:
            break;
        case LSTORE_2:
            break;
        case LSTORE_3:
            break;
        case LSUB:
            break;
        case LUSHR:
            break;
        case LXOR:
            break;
        case MONITORENTER:
            break;
        case MONITOREXIT:
            break;
        case MULTIANEWARRAY:
            break;
        case NEW:
            break;
        case NEWARRAY:
            break;
        case NOP:
            break;
        case POP:
            break;
        case POP2:
            break;
        case PUTFIELD:
            break;
        case PUTSTATIC:
            break;
        case RET:
            break;
        case RETURN:
            break;
        case SALOAD:
            break;
        case SASTORE:
            break;
        case SIPUSH:
            break;
        case SWAP:
            break;
        case TABLESWITCH:
            break;
        default:
            break;

        }
        return null;
    }

    private static Attribute readAnnotations(ClassNode owner, AttributeType type, DataInputStream is, int nameIndex,
            int length) throws IOException {
        boolean param = type == AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS
                || type == AttributeType.RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS;
        boolean invisible = type == AttributeType.RUNTIME_INVISIBLE_ANNOTATIONS
                || type == AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS;
        int num = param ? is.readUnsignedByte() : is.readUnsignedShort();
        switch (type) {
        case RUNTIME_INVISIBLE_ANNOTATIONS:
        case RUNTIME_VISIBLE_ANNOTATIONS:
            List<Annotation> annotations = Lists.newArrayList();
            for (int i = 0; i < num; i++) {
                annotations.add(readAnnotation(owner, is));
            }
            return new AttributeAnnotations(nameIndex, invisible, annotations);
        case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
        case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
            List<ParameterAnnotations> paramAnnotations = Lists.newArrayList();
            for (int i = 0; i < num; i++) {
                paramAnnotations.add(readParameterAnnotations(owner, is));
            }
            return new AttributeParameterAnnotations(nameIndex, invisible, paramAnnotations);

        default:
            throw new RuntimeException("UNKNOWN ANNOTATION: " + type.name());
        }
    }

    private static Annotation readAnnotation(ClassNode owner, DataInputStream is) throws IOException {
        int typeIndex = is.readUnsignedShort();
        int num = is.readUnsignedShort();
        List<ElementValuePair> valuePairs = Lists.newArrayList();
        for (int i = 0; i < num; i++) {
            int nameIndex = is.readUnsignedShort();
            ElementValue value = readElementValue(owner, is);
            valuePairs.add(new ElementValuePair(nameIndex, value));
        }
        return new Annotation(typeIndex, valuePairs);
    }

    private static ElementValue readElementValue(ClassNode owner, DataInputStream is) throws IOException {
        char tag = (char) is.readUnsignedByte();
        ElementValueType type = ElementValueType.fromType(tag);
        if (type == null) { throw new RuntimeException("UNKNOWN ANNOTATION ELEMENT TAG: " + tag); }
        switch (type) {
        case CONST_VALUE_INDEX_BYTE:
        case CONST_VALUE_INDEX_C:
        case CONST_VALUE_INDEX_DOUBLE:
        case CONST_VALUE_INDEX_FLOAT:
        case CONST_VALUE_INDEX_INT:
        case CONST_VALUE_INDEX_LONG:
        case CONST_VALUE_INDEX_SHORT:
        case CONST_VALUE_INDEX_BOOLEAN:
        case CONST_VALUE_INDEX_s:
            int constIndex = is.readUnsignedShort();
            return new ElementValueConstValueIndex(type, constIndex);
        case ENUM_CONST_VALUE:
            int typeIndex = is.readUnsignedShort();
            int constNameIndex = is.readUnsignedShort();
            return new ElementValueEnumConstValue(typeIndex, constNameIndex);
        case CLASS_INFO_INDEX:
            int infoIndex = is.readUnsignedShort();
            return new ElementValueClassInfoIndex(infoIndex);
        case ANNOTATION_VALUE:
            Annotation annotation = readAnnotation(owner, is);
            return new ElementValueAnnotationValue(annotation);
        case ARRAY_VALUE:
            int num = is.readUnsignedShort();
            List<ElementValue> values = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                values.add(readElementValue(owner, is));
            }
            return new ElementValueArrayValue(values);
        default:
            throw new RuntimeException("UNKNOWN ANNOTATION ELEMENT TYPE: " + type.name());

        }
    }

    private static ParameterAnnotations readParameterAnnotations(ClassNode owner, DataInputStream is)
            throws IOException {
        int num = is.readUnsignedShort();
        List<Annotation> annotations = Lists.newArrayList();
        for (int i = 0; i < num; i++) {
            annotations.add(readAnnotation(owner, is));
        }
        return new ParameterAnnotations(annotations);
    }

    private static Constant readConst(ConstantType constType, DataInputStream is) throws IOException {
        // Braces in switches are ugly, but having variable name pass-through
        // sucks.
        switch (constType) {
        case DOUBLE: {
            double value = is.readDouble();
            return new ConstDouble(value);
        }
        case LONG: {
            long value = is.readLong();
            return new ConstLong(value);
        }
        case FLOAT: {
            float value = is.readFloat();
            return new ConstFloat(value);
        }
        case INT: {
            int value = is.readInt();
            return new ConstInt(value);
        }
        case STRING: {
            int index = is.readUnsignedShort();
            return new ConstString(index);
        }
        case UTF8: {
            int length = is.readUnsignedShort();
            byte[] data = new byte[length];
            is.read(data);
            return new ConstUTF8(new String(data));
        }
        case CLASS: {
            int name = is.readUnsignedShort();
            return new ConstClass(name);
        }
        case FIELD: {
            int clazz = is.readUnsignedShort();
            int nameType = is.readUnsignedShort();
            ConstField cf = new ConstField(clazz, nameType);
            return cf;
        }
        case METHOD: {
            int clazz = is.readUnsignedShort();
            int nameType = is.readUnsignedShort();
            return new ConstMethod(clazz, nameType);
        }
        case INTERFACE_METHOD: {
            int clazz = is.readUnsignedShort();
            int nameType = is.readUnsignedShort();
            return new ConstInterfaceMethod(clazz, nameType);
        }
        case INVOKEDYNAMIC: {
            int attribute = is.readUnsignedShort();
            int nameType = is.readUnsignedShort();
            return new ConstInvokeDynamic(attribute, nameType);
        }
        case METHOD_HANDLE: {
            int kind = is.readUnsignedByte();
            int index = is.readUnsignedShort();
            return new ConstMethodHandle(kind, index);
        }
        case METHOD_TYPE: {
            int type = is.readUnsignedShort();
            return new ConstMethodType(type);
        }
        case NAME_TYPE: {
            int name = is.readUnsignedShort();
            int desc = is.readUnsignedShort();
            return new ConstNameType(name, desc);
        }
        default:
            break;
        }
        return null;
    }
}