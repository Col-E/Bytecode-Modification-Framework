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
import io.github.bmf.consts.opcode.OpcodeInst;
import io.github.bmf.consts.opcode.impl.*;
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
        switch (code) {
        case Opcode.NOP:
            return OpcodeInst.NOP;
        case Opcode.ACONST_NULL:
            return OpcodeInst.ACONST_NULL;
        case Opcode.ICONST_M1:
            return OpcodeInst.ICONST_M1;
        case Opcode.ICONST_0:
            return OpcodeInst.ICONST_0;
        case Opcode.ICONST_1:
            return OpcodeInst.ICONST_1;
        case Opcode.ICONST_2:
            return OpcodeInst.ICONST_2;
        case Opcode.ICONST_3:
            return OpcodeInst.ICONST_3;
        case Opcode.ICONST_4:
            return OpcodeInst.ICONST_4;
        case Opcode.ICONST_5:
            return OpcodeInst.ICONST_5;
        case Opcode.LCONST_0:
            return OpcodeInst.LCONST_0;
        case Opcode.LCONST_1:
            return OpcodeInst.LCONST_1;
        case Opcode.FCONST_0:
            return OpcodeInst.FCONST_0;
        case Opcode.FCONST_1:
            return OpcodeInst.FCONST_1;
        case Opcode.FCONST_2:
            return OpcodeInst.FCONST_2;
        case Opcode.DCONST_0:
            return OpcodeInst.DCONST_0;
        case Opcode.DCONST_1:
            return OpcodeInst.DCONST_1;
        case Opcode.BIPUSH:
            int byteVal = is.readUnsignedByte();
            return new BIPUSH(byteVal);
        case Opcode.SIPUSH:
            int shortVal = is.readUnsignedShort();
            return new SIPUSH(shortVal);
        case Opcode.LDC: {
            int index = is.readUnsignedByte();
            return new LDC(index);
        }
        case Opcode.LDC_W: 
        case Opcode.LDC2_W:{
            int indexbyte1 = is.readUnsignedByte();
            int indexbyte2 = is.readUnsignedByte();
            int index = indexbyte1 << 8 + indexbyte2;
            return code == Opcode.LDC2_W ? new LDC2_W(index) : new LDC_W(index);
        }
        case Opcode.ILOAD:
        case Opcode.LLOAD:
        case Opcode.FLOAD:
        case Opcode.DLOAD:
        case Opcode.ALOAD:
        case Opcode.ILOAD_0:
        case Opcode.ILOAD_1:
        case Opcode.ILOAD_2:
        case Opcode.ILOAD_3:
        case Opcode.LLOAD_0:
        case Opcode.LLOAD_1:
        case Opcode.LLOAD_2:
        case Opcode.LLOAD_3:
        case Opcode.FLOAD_0:
        case Opcode.FLOAD_1:
        case Opcode.FLOAD_2:
        case Opcode.FLOAD_3:
        case Opcode.DLOAD_0:
        case Opcode.DLOAD_1:
        case Opcode.DLOAD_2:
        case Opcode.DLOAD_3:
        case Opcode.ALOAD_0:
        case Opcode.ALOAD_1:
        case Opcode.ALOAD_2:
        case Opcode.ALOAD_3:
        case Opcode.IALOAD:
        case Opcode.LALOAD:
        case Opcode.FALOAD:
        case Opcode.DALOAD:
        case Opcode.AALOAD:
        case Opcode.BALOAD:
        case Opcode.CALOAD:
        case Opcode.SALOAD:
        case Opcode.ISTORE:
        case Opcode.LSTORE:
        case Opcode.FSTORE:
        case Opcode.DSTORE:
        case Opcode.ASTORE:
        case Opcode.ISTORE_0:
        case Opcode.ISTORE_1:
        case Opcode.ISTORE_2:
        case Opcode.ISTORE_3:
        case Opcode.LSTORE_0:
        case Opcode.LSTORE_1:
        case Opcode.LSTORE_2:
        case Opcode.LSTORE_3:
        case Opcode.FSTORE_0:
        case Opcode.FSTORE_1:
        case Opcode.FSTORE_2:
        case Opcode.FSTORE_3:
        case Opcode.DSTORE_0:
        case Opcode.DSTORE_1:
        case Opcode.DSTORE_2:
        case Opcode.DSTORE_3:
        case Opcode.ASTORE_0:
        case Opcode.ASTORE_1:
        case Opcode.ASTORE_2:
        case Opcode.ASTORE_3:
        case Opcode.IASTORE:
        case Opcode.LASTORE:
        case Opcode.FASTORE:
        case Opcode.DASTORE:
        case Opcode.AASTORE:
        case Opcode.BASTORE:
        case Opcode.CASTORE:
        case Opcode.SASTORE:
        case Opcode.POP:
        case Opcode.POP2:
        case Opcode.DUP:
        case Opcode.DUP_X1:
        case Opcode.DUP_X2:
        case Opcode.DUP2:
        case Opcode.DUP2_X1:
        case Opcode.DUP2_X2:
        case Opcode.SWAP:
        case Opcode.IADD:
        case Opcode.LADD:
        case Opcode.FADD:
        case Opcode.DADD:
        case Opcode.ISUB:
        case Opcode.LSUB:
        case Opcode.FSUB:
        case Opcode.DSUB:
        case Opcode.IMUL:
        case Opcode.LMUL:
        case Opcode.FMUL:
        case Opcode.DMUL:
        case Opcode.IDIV:
        case Opcode.LDIV:
        case Opcode.FDIV:
        case Opcode.DDIV:
        case Opcode.IREM:
        case Opcode.LREM:
        case Opcode.FREM:
        case Opcode.DREM:
        case Opcode.INEG:
        case Opcode.LNEG:
        case Opcode.FNEG:
        case Opcode.DNEG:
        case Opcode.ISHL:
        case Opcode.LSHL:
        case Opcode.ISHR:
        case Opcode.LSHR:
        case Opcode.IUSHR:
        case Opcode.LUSHR:
        case Opcode.IAND:
        case Opcode.LAND:
        case Opcode.IOR:
        case Opcode.LOR:
        case Opcode.IXOR:
        case Opcode.LXOR:
        case Opcode.IINC:
        case Opcode.I2L:
        case Opcode.I2F:
        case Opcode.I2D:
        case Opcode.L2I:
        case Opcode.L2F:
        case Opcode.L2D:
        case Opcode.F2I:
        case Opcode.F2L:
        case Opcode.F2D:
        case Opcode.D2I:
        case Opcode.D2L:
        case Opcode.D2F:
        case Opcode.I2B:
        case Opcode.I2C:
        case Opcode.I2S:
        case Opcode.LCMP:
        case Opcode.FCMPL:
        case Opcode.FCMPG:
        case Opcode.DCMPL:
        case Opcode.DCMPG:
        case Opcode.IFEQ:
        case Opcode.IFNE:
        case Opcode.IFLT:
        case Opcode.IFGE:
        case Opcode.IFGT:
        case Opcode.IFLE:
        case Opcode.IF_ICMPEQ:
        case Opcode.IF_ICMPNE:
        case Opcode.IF_ICMPLT:
        case Opcode.IF_ICMPGE:
        case Opcode.IF_ICMPGT:
        case Opcode.IF_ICMPLE:
        case Opcode.IF_ACMPEQ:
        case Opcode.IF_ACMPNE:
        case Opcode.GOTO:
        case Opcode.JSR:
        case Opcode.RET:
        case Opcode.TABLESWITCH:
        case Opcode.LOOKUPSWITCH:
        case Opcode.IRETURN:
        case Opcode.LRETURN:
        case Opcode.FRETURN:
        case Opcode.DRETURN:
        case Opcode.ARETURN:
        case Opcode.RETURN:
        case Opcode.GETSTATIC:
        case Opcode.PUTSTATIC:
        case Opcode.GETFIELD:
        case Opcode.PUTFIELD:
        case Opcode.INVOKEVIRTUAL:
        case Opcode.INVOKESPECIAL:
        case Opcode.INVOKESTATIC:
        case Opcode.INVOKEINTERFACE:
        case Opcode.NEW:
        case Opcode.NEWARRAY:
        case Opcode.ANEWARRAY:
        case Opcode.ARRAYLENGTH:
        case Opcode.ATHROW:
        case Opcode.CHECKCAST:
        case Opcode.INSTANCEOF:
        case Opcode.MONITORENTER:
        case Opcode.MONITOREXIT:
        case Opcode.WIDE:
        case Opcode.MULTIANEWARRAY:
        case Opcode.IFNULL:
        case Opcode.IFNONNULL:
        case Opcode.GOTO_W:
        case Opcode.JSR_W:
        case Opcode.BREAKPOINT:
        case Opcode.IMPDEP1:
        case Opcode.IMPDEP2:
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