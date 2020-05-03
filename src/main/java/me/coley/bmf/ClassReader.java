package me.coley.bmf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.coley.bmf.attribute.*;
import me.coley.bmf.attribute.annotation.*;
import me.coley.bmf.attribute.annotation.element.*;
import me.coley.bmf.attribute.clazz.*;
import me.coley.bmf.attribute.field.AttributeConstantValue;
import me.coley.bmf.attribute.method.*;
import me.coley.bmf.attribute.method.AttributeStackMapTable.*;
import me.coley.bmf.consts.*;
import me.coley.bmf.exception.InvalidClassException;
import me.coley.bmf.insn.Instruction;
import me.coley.bmf.insn.InsnInstances;
import me.coley.bmf.insn.impl.*;
import me.coley.bmf.util.IndexableDataStream;
import me.coley.bmf.util.StreamUtil;

/**
 * @author Matt
 */
@SuppressWarnings("rawtypes")
public class ClassReader {
    public static ClassNode getNode(byte[] data) throws InvalidClassException, IOException {
        IndexableDataStream is = StreamUtil.fromBytes(data);
        if (is.readInt() != 0xCAFEBABE) {
            throw new InvalidClassException("Does not start with 0xCAFEBABE");
        }
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
            if ((type == ConstantType.DOUBLE) || (type == ConstantType.LONG)) {
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
        for (int i = 0; i < interfaceLength; i++) {
            node.addInterfaceIndex(is.readUnsignedShort());
        }
        // Read fields
        int fieldLength = is.readUnsignedShort();
        for (int i = 0; i < fieldLength; i++) {
            FieldNode field = readField(node, is);
            node.addField(field);
        }
        // Read methods
        int methodLength = is.readUnsignedShort();
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

    private static FieldNode readField(ClassNode owner, IndexableDataStream is)
            throws IOException, InvalidClassException {
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

    private static MethodNode readMethod(ClassNode owner, IndexableDataStream is)
            throws IOException, InvalidClassException {
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

    private static Attribute readAttribute(ClassNode owner, IndexableDataStream is)
            throws IOException, InvalidClassException {
        int nameIndex = is.readUnsignedShort();
        int length = is.readInt();
        String name = owner.getConst(nameIndex).getValue().toString();
        AttributeType attributeType = AttributeType.fromName(name);
        if (attributeType == null) {
            throw new InvalidClassException("Unknown attribute: " + name);
        }
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
                bsMethods.add(bsm);
            }
            return new AttributeBootstrapMethods(nameIndex, bsMethods);
        }
        case CODE: {
            int maxStack = is.readUnsignedShort();
            int maxLocals = is.readUnsignedShort();
            MethodCode codeData = readMethodCode(owner, is);
            List<MethodException> exceptions = new ArrayList<>();
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
            List<Attribute> attributes = new ArrayList<>();
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
            List<Integer> exceptionIndices = new ArrayList<>();
            for (int i = 0; i < exceptionCount; i++) {
                exceptionIndices.add(is.readUnsignedShort());
            }
            return new AttributeExceptions(nameIndex, exceptionIndices);
        }
        case INNER_CLASSES: {
            int classCount = is.readUnsignedShort();
            List<InnerClass> classes = new ArrayList<>();
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
            List<LocalVariableType> localTypes = new ArrayList<>();
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
            List<Integer> data = new ArrayList<>();
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
            return readStackMapTable(nameIndex, is);
        }
        case SYNTHETIC: {
            return new AttributeSynthetic(nameIndex);
        }
        default:
            break;
        }
        throw new RuntimeException("Unhandled attribute! " + attributeType);
    }

    private static MethodCode readMethodCode(ClassNode owner, IndexableDataStream is) throws IOException {
        int codeLength = is.readInt();
        // Store original instruction bytes
        byte[] origInstructionBytes = new byte[codeLength];
        is.read(origInstructionBytes);
        MethodCode codeData = new MethodCode(origInstructionBytes);
        // TODO: Verify this on test cases
        boolean parseInstructions = false;
        if (parseInstructions) {
            // Create instruction data
            List<Instruction> instructions = new ArrayList<>();
            IndexableDataStream opstr = StreamUtil.fromBytes(codeData.original);
            // Is there a way to find the # of opcodes? Can't seem to figure one
            // out aside from reading and knowing after the fact.
            while (opstr.available() > 0) {
                try {
                    Instruction op = readInstruction(opstr);
                    instructions.add(op);
                } catch (Exception e) {
                    String className = owner.getName();
                    System.err.println("<" + className + "> Failed at (Skipped instruction parsing): " + instructions.size());
                    e.printStackTrace();
                    return codeData;
                }
            }
            codeData.instructions = instructions;
            opstr.close();
        }
        return codeData;
    }

    private static Instruction readInstruction(IndexableDataStream is) throws IOException {
        int code = is.readUnsignedByte();
        switch (code) {
        case Instruction.NOP:
            return InsnInstances.NOP;
        case Instruction.ACONST_NULL:
            return InsnInstances.ACONST_NULL;
        case Instruction.ICONST_M1:
            return InsnInstances.ICONST_M1;
        case Instruction.ICONST_0:
            return InsnInstances.ICONST_0;
        case Instruction.ICONST_1:
            return InsnInstances.ICONST_1;
        case Instruction.ICONST_2:
            return InsnInstances.ICONST_2;
        case Instruction.ICONST_3:
            return InsnInstances.ICONST_3;
        case Instruction.ICONST_4:
            return InsnInstances.ICONST_4;
        case Instruction.ICONST_5:
            return InsnInstances.ICONST_5;
        case Instruction.LCONST_0:
            return InsnInstances.LCONST_0;
        case Instruction.LCONST_1:
            return InsnInstances.LCONST_1;
        case Instruction.FCONST_0:
            return InsnInstances.FCONST_0;
        case Instruction.FCONST_1:
            return InsnInstances.FCONST_1;
        case Instruction.FCONST_2:
            return InsnInstances.FCONST_2;
        case Instruction.DCONST_0:
            return InsnInstances.DCONST_0;
        case Instruction.DCONST_1:
            return InsnInstances.DCONST_1;
        case Instruction.BIPUSH:
            int byteVal = is.readUnsignedByte();
            return new BIPUSH(byteVal);
        case Instruction.SIPUSH:
            int shortVal = is.readUnsignedShort();
            return new SIPUSH(shortVal);
        case Instruction.LDC: {
            int index = is.readUnsignedByte();
            return new LDC(index);
        }
        case Instruction.LDC_W:
        case Instruction.LDC2_W: {
            int index = is.readUnsignedShort();
            return code == Instruction.LDC2_W ? new LDC2_W(index) : new LDC_W(index);
        }
        case Instruction.ILOAD: {
            int index = is.readUnsignedByte();
            return new ILOAD(index);
        }
        case Instruction.LLOAD: {
            int index = is.readUnsignedByte();
            return new LLOAD(index);
        }
        case Instruction.FLOAD: {
            int index = is.readUnsignedByte();
            return new FLOAD(index);
        }
        case Instruction.DLOAD: {
            int index = is.readUnsignedByte();
            return new DLOAD(index);
        }
        case Instruction.ALOAD: {
            int index = is.readUnsignedByte();
            return new ALOAD(index);
        }
        case Instruction.ILOAD_0:
            return InsnInstances.ILOAD_0;
        case Instruction.ILOAD_1:
            return InsnInstances.ILOAD_1;
        case Instruction.ILOAD_2:
            return InsnInstances.ILOAD_2;
        case Instruction.ILOAD_3:
            return InsnInstances.ILOAD_3;
        case Instruction.LLOAD_0:
            return InsnInstances.LLOAD_0;
        case Instruction.LLOAD_1:
            return InsnInstances.LLOAD_1;
        case Instruction.LLOAD_2:
            return InsnInstances.LLOAD_2;
        case Instruction.LLOAD_3:
            return InsnInstances.LLOAD_3;
        case Instruction.FLOAD_0:
            return InsnInstances.FLOAD_0;
        case Instruction.FLOAD_1:
            return InsnInstances.FLOAD_1;
        case Instruction.FLOAD_2:
            return InsnInstances.FLOAD_2;
        case Instruction.FLOAD_3:
            return InsnInstances.FLOAD_3;
        case Instruction.DLOAD_0:
            return InsnInstances.DLOAD_0;
        case Instruction.DLOAD_1:
            return InsnInstances.DLOAD_1;
        case Instruction.DLOAD_2:
            return InsnInstances.DLOAD_2;
        case Instruction.DLOAD_3:
            return InsnInstances.DLOAD_3;
        case Instruction.ALOAD_0:
            return InsnInstances.ALOAD_0;
        case Instruction.ALOAD_1:
            return InsnInstances.ALOAD_1;
        case Instruction.ALOAD_2:
            return InsnInstances.ALOAD_2;
        case Instruction.ALOAD_3:
            return InsnInstances.ALOAD_3;
        case Instruction.ISTORE: {
            int index = is.readUnsignedByte();
            return new ISTORE(index);
        }
        case Instruction.LSTORE: {
            int index = is.readUnsignedByte();
            return new LSTORE(index);
        }
        case Instruction.FSTORE: {
            int index = is.readUnsignedByte();
            return new FSTORE(index);
        }
        case Instruction.DSTORE: {
            int index = is.readUnsignedByte();
            return new DSTORE(index);
        }
        case Instruction.ASTORE: {
            int index = is.readUnsignedByte();
            return new ASTORE(index);
        }
        case Instruction.ISTORE_0:
            return InsnInstances.ISTORE_0;
        case Instruction.ISTORE_1:
            return InsnInstances.ISTORE_1;
        case Instruction.ISTORE_2:
            return InsnInstances.ISTORE_2;
        case Instruction.ISTORE_3:
            return InsnInstances.ISTORE_3;
        case Instruction.LSTORE_0:
            return InsnInstances.LSTORE_0;
        case Instruction.LSTORE_1:
            return InsnInstances.LSTORE_1;
        case Instruction.LSTORE_2:
            return InsnInstances.LSTORE_2;
        case Instruction.LSTORE_3:
            return InsnInstances.LSTORE_3;
        case Instruction.FSTORE_0:
            return InsnInstances.FSTORE_0;
        case Instruction.FSTORE_1:
            return InsnInstances.FSTORE_1;
        case Instruction.FSTORE_2:
            return InsnInstances.FSTORE_2;
        case Instruction.FSTORE_3:
            return InsnInstances.FSTORE_3;
        case Instruction.DSTORE_0:
            return InsnInstances.DSTORE_0;
        case Instruction.DSTORE_1:
            return InsnInstances.DSTORE_1;
        case Instruction.DSTORE_2:
            return InsnInstances.DSTORE_2;
        case Instruction.DSTORE_3:
            return InsnInstances.DSTORE_3;
        case Instruction.ASTORE_0:
            return InsnInstances.ASTORE_0;
        case Instruction.ASTORE_1:
            return InsnInstances.ASTORE_1;
        case Instruction.ASTORE_2:
            return InsnInstances.ASTORE_2;
        case Instruction.ASTORE_3:
            return InsnInstances.ASTORE_3;
        case Instruction.IALOAD:
            return InsnInstances.IALOAD;
        case Instruction.LALOAD:
            return InsnInstances.LALOAD;
        case Instruction.FALOAD:
            return InsnInstances.FALOAD;
        case Instruction.DALOAD:
            return InsnInstances.DALOAD;
        case Instruction.AALOAD:
            return InsnInstances.AALOAD;
        case Instruction.BALOAD:
            return InsnInstances.BALOAD;
        case Instruction.CALOAD:
            return InsnInstances.CALOAD;
        case Instruction.SALOAD:
            return InsnInstances.SALOAD;
        case Instruction.IASTORE:
            return InsnInstances.IASTORE;
        case Instruction.LASTORE:
            return InsnInstances.LASTORE;
        case Instruction.FASTORE:
            return InsnInstances.FASTORE;
        case Instruction.DASTORE:
            return InsnInstances.DASTORE;
        case Instruction.AASTORE:
            return InsnInstances.AASTORE;
        case Instruction.BASTORE:
            return InsnInstances.BASTORE;
        case Instruction.CASTORE:
            return InsnInstances.CASTORE;
        case Instruction.SASTORE:
            return InsnInstances.SASTORE;
        case Instruction.POP:
            return InsnInstances.POP;
        case Instruction.POP2:
            return InsnInstances.POP2;
        case Instruction.DUP:
            return InsnInstances.DUP;
        case Instruction.DUP_X1:
            return InsnInstances.DUP_X1;
        case Instruction.DUP_X2:
            return InsnInstances.DUP_X2;
        case Instruction.DUP2:
            return InsnInstances.DUP2;
        case Instruction.DUP2_X1:
            return InsnInstances.DUP2_X1;
        case Instruction.DUP2_X2:
            return InsnInstances.DUP2_X2;
        case Instruction.SWAP:
            return InsnInstances.SWAP;
        case Instruction.IADD:
            return InsnInstances.IADD;
        case Instruction.LADD:
            return InsnInstances.LADD;
        case Instruction.FADD:
            return InsnInstances.FADD;
        case Instruction.DADD:
            return InsnInstances.DADD;
        case Instruction.ISUB:
            return InsnInstances.ISUB;
        case Instruction.LSUB:
            return InsnInstances.LSUB;
        case Instruction.FSUB:
            return InsnInstances.FSUB;
        case Instruction.DSUB:
            return InsnInstances.DSUB;
        case Instruction.IMUL:
            return InsnInstances.IMUL;
        case Instruction.LMUL:
            return InsnInstances.LMUL;
        case Instruction.FMUL:
            return InsnInstances.FMUL;
        case Instruction.DMUL:
            return InsnInstances.DMUL;
        case Instruction.IDIV:
            return InsnInstances.IDIV;
        case Instruction.LDIV:
            return InsnInstances.LDIV;
        case Instruction.FDIV:
            return InsnInstances.FDIV;
        case Instruction.DDIV:
            return InsnInstances.DDIV;
        case Instruction.IREM:
            return InsnInstances.IREM;
        case Instruction.LREM:
            return InsnInstances.LREM;
        case Instruction.FREM:
            return InsnInstances.FREM;
        case Instruction.DREM:
            return InsnInstances.DREM;
        case Instruction.INEG:
            return InsnInstances.INEG;
        case Instruction.LNEG:
            return InsnInstances.LNEG;
        case Instruction.FNEG:
            return InsnInstances.FNEG;
        case Instruction.DNEG:
            return InsnInstances.DNEG;
        case Instruction.ISHL:
            return InsnInstances.ISHL;
        case Instruction.LSHL:
            return InsnInstances.LSHL;
        case Instruction.ISHR:
            return InsnInstances.ISHR;
        case Instruction.LSHR:
            return InsnInstances.LSHR;
        case Instruction.IUSHR:
            return InsnInstances.IUSHR;
        case Instruction.LUSHR:
            return InsnInstances.LUSHR;
        case Instruction.IAND:
            return InsnInstances.IAND;
        case Instruction.LAND:
            return InsnInstances.LAND;
        case Instruction.IOR:
            return InsnInstances.IOR;
        case Instruction.LOR:
            return InsnInstances.LOR;
        case Instruction.IXOR:
            return InsnInstances.IXOR;
        case Instruction.LXOR:
            return InsnInstances.LXOR;
        case Instruction.IINC: {
            int index = is.readUnsignedByte();
            int increment = is.readUnsignedByte();
            return new IINC(index, increment);
        }
        case Instruction.I2L:
            return InsnInstances.I2L;
        case Instruction.I2F:
            return InsnInstances.I2F;
        case Instruction.I2D:
            return InsnInstances.I2D;
        case Instruction.L2I:
            return InsnInstances.L2I;
        case Instruction.L2F:
            return InsnInstances.L2F;
        case Instruction.L2D:
            return InsnInstances.L2D;
        case Instruction.F2I:
            return InsnInstances.F2I;
        case Instruction.F2L:
            return InsnInstances.F2L;
        case Instruction.F2D:
            return InsnInstances.F2D;
        case Instruction.D2I:
            return InsnInstances.D2I;
        case Instruction.D2L:
            return InsnInstances.D2L;
        case Instruction.D2F:
            return InsnInstances.D2F;
        case Instruction.I2B:
            return InsnInstances.I2B;
        case Instruction.I2C:
            return InsnInstances.I2C;
        case Instruction.I2S:
            return InsnInstances.I2S;
        case Instruction.LCMP:
            return InsnInstances.LCMP;
        case Instruction.FCMPL:
            return InsnInstances.FCMPL;
        case Instruction.FCMPG:
            return InsnInstances.FCMPG;
        case Instruction.DCMPL:
            return InsnInstances.DCMPL;
        case Instruction.DCMPG:
            return InsnInstances.DCMPG;
        case Instruction.IRETURN:
            return InsnInstances.IRETURN;
        case Instruction.LRETURN:
            return InsnInstances.LRETURN;
        case Instruction.FRETURN:
            return InsnInstances.FRETURN;
        case Instruction.DRETURN:
            return InsnInstances.DRETURN;
        case Instruction.ARETURN:
            return InsnInstances.ARETURN;
        case Instruction.RETURN:
            return InsnInstances.RETURN;
        case Instruction.IFEQ:
            return new IFEQ(is.readShort());
        case Instruction.IFNE:
            return new IFNE(is.readShort());
        case Instruction.IFLT:
            return new IFLT(is.readShort());
        case Instruction.IFGE:
            return new IFGE(is.readShort());
        case Instruction.IFGT:
            return new IFGT(is.readShort());
        case Instruction.IFLE:
            return new IFLE(is.readShort());
        case Instruction.IF_ICMPEQ:
            return new IF_ICMPEQ(is.readShort());
        case Instruction.IF_ICMPNE:
            return new IF_ICMPNE(is.readShort());
        case Instruction.IF_ICMPLT:
            return new IF_ICMPLT(is.readShort());
        case Instruction.IF_ICMPGE:
            return new IF_ICMPGE(is.readShort());
        case Instruction.IF_ICMPGT:
            return new IF_ICMPGT(is.readShort());
        case Instruction.IF_ICMPLE:
            return new IF_ICMPLE(is.readShort());
        case Instruction.IF_ACMPEQ:
            return new IF_ACMPEQ(is.readShort());
        case Instruction.IF_ACMPNE:
            return new IF_ACMPNE(is.readShort());
        case Instruction.IFNULL:
            return new IFNULL(is.readShort());
        case Instruction.IFNONNULL:
            return new IFNONNULL(is.readShort());
        case Instruction.GOTO:
        case Instruction.GOTO_W:
            return new GOTO(code == Instruction.GOTO_W ? is.readInt() : is.readShort());
        case Instruction.TABLESWITCH: {
            is.skip((4 - (is.getIndex() % 4)) % 4);
            int default_offset = is.readInt();
            int low = is.readInt();
            int high = is.readInt();
            int dif = (high - low + 1);
            List<Integer> offsets = new ArrayList<>();
            for (int i = 0; i < dif; i++) {
                int x = is.readInt();
                offsets.add(x);
            }
            return new TABLESWITCH(default_offset, low, high, offsets);
        }
        case Instruction.LOOKUPSWITCH: {
            is.skip((4 - (is.getIndex() % 4)) % 4);
            int default_offset = is.readInt();
            int n = is.readInt();
            List<LOOKUPSWITCH.OffsetPair> offsets = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int key = is.readInt();
                int offset = is.readInt();
                offsets.add(new LOOKUPSWITCH.OffsetPair(key, offset));
            }
            return new LOOKUPSWITCH(default_offset, offsets);
        }
        case Instruction.GETSTATIC:
            return new GETSTATIC(is.readUnsignedShort());
        case Instruction.PUTSTATIC:
            return new PUTSTATIC(is.readUnsignedShort());
        case Instruction.GETFIELD:
            return new GETFIELD(is.readUnsignedShort());
        case Instruction.PUTFIELD:
            return new PUTFIELD(is.readUnsignedShort());
        case Instruction.INVOKEVIRTUAL:
            return new INVOKEVIRTUAL(is.readUnsignedShort());
        case Instruction.INVOKESTATIC:
            return new INVOKESTATIC(is.readUnsignedShort());
        case Instruction.INVOKESPECIAL:
            return new INVOKESPECIAL(is.readUnsignedShort());
        case Instruction.INVOKEINTERFACE:
            return new INVOKEINTERFACE(is.readUnsignedShort(), is.readUnsignedByte(), is.readUnsignedByte());
        case Instruction.INVOKEDYNAMIC:
            INVOKEDYNAMIC indy = new INVOKEDYNAMIC(is.readUnsignedShort());
            // Discard two bytes that are always 0.
            is.readUnsignedByte();
            is.readUnsignedByte();
            return indy;
        case Instruction.NEW:
            return new NEW(is.readUnsignedShort());
        case Instruction.NEWARRAY:
            return new NEWARRAY(is.readUnsignedByte());
        case Instruction.ANEWARRAY:
            return new ANEWARRAY(is.readUnsignedShort());
        case Instruction.MULTIANEWARRAY:
            return new MULTIANEWARRAY(is.readUnsignedShort(), is.readUnsignedByte());
        case Instruction.ARRAYLENGTH:
            return InsnInstances.ARRAYLENGTH;
        case Instruction.ATHROW:
            return InsnInstances.ATHROW;
        case Instruction.CHECKCAST:
            return new CHECKCAST(is.readUnsignedShort());
        case Instruction.INSTANCEOF:
            return new INSTANCEOF(is.readUnsignedShort());
        case Instruction.MONITORENTER:
            return InsnInstances.MONITORENTER;
        case Instruction.MONITOREXIT:
            return InsnInstances.MONITOREXIT;
        case Instruction.BREAKPOINT:
            return InsnInstances.BREAKPOINT;
        case Instruction.IMPDEP1:
            return InsnInstances.IMPDEP1;
        case Instruction.IMPDEP2:
            return InsnInstances.IMPDEP2;
        case Instruction.WIDE: {
            int opcode = is.readUnsignedByte();
            int varnum = is.readShort();
            // TODO: Verify we write wide instructions properly
            //  - GOTO_W
            //  - LDC_W
            //  - LDC2_W
            int next = is.readUnsignedByte();
            is.reset(1);
            if (next == Instruction.IINC) {
                is.skipBytes(6);
                return new WIDE(opcode, varnum, 6);
            } else {
                is.skipBytes(4);
                return new WIDE(opcode, varnum, 4);
            }
        }
        case Instruction.RET:
        case Instruction.JSR:
        case Instruction.JSR_W:
            throw new RuntimeException("Unsupported: Outdated (Pre-Java 7) Opcode. Get with the times.");
        }
        // (TODO: Figure out why this happens and why it's super rare)
        System.out.println("UNKNOWN CODE: " + code + " ... Substituting with NOP");
        return InsnInstances.NOP;
    }

    private static Attribute readAnnotations(ClassNode owner, AttributeType type, IndexableDataStream is, int nameIndex,
            int length) throws IOException {
        boolean param = (type == AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS)
                || (type == AttributeType.RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS);
        boolean invisible = (type == AttributeType.RUNTIME_INVISIBLE_ANNOTATIONS)
                || (type == AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS);
        int num = param ? is.readUnsignedByte() : is.readUnsignedShort();
        switch (type) {
        case RUNTIME_INVISIBLE_ANNOTATIONS:
        case RUNTIME_VISIBLE_ANNOTATIONS:
            List<Annotation> annotations = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                annotations.add(readAnnotation(owner, is));
            }
            return new AttributeAnnotations(nameIndex, invisible, annotations);
        case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
        case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
            List<ParameterAnnotations> paramAnnotations = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                paramAnnotations.add(readParameterAnnotations(owner, is));
            }
            return new AttributeParameterAnnotations(nameIndex, invisible, paramAnnotations);

        default:
            throw new RuntimeException("UNKNOWN ANNOTATION: " + type.name());
        }
    }

    private static Annotation readAnnotation(ClassNode owner, IndexableDataStream is) throws IOException {
        int typeIndex = is.readUnsignedShort();
        int num = is.readUnsignedShort();
        List<ElementValuePair> valuePairs = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            int nameIndex = is.readUnsignedShort();
            ElementValue value = readElementValue(owner, is);
            valuePairs.add(new ElementValuePair(nameIndex, value));
        }
        return new Annotation(typeIndex, valuePairs);
    }

    private static ElementValue readElementValue(ClassNode owner, IndexableDataStream is) throws IOException {
        char tag = (char) is.readUnsignedByte();
        ElementValueType type = ElementValueType.fromType(tag);
        if (type == null) {
            throw new RuntimeException("UNKNOWN ANNOTATION ELEMENT TAG: " + tag);
        }
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

    private static ParameterAnnotations readParameterAnnotations(ClassNode owner, IndexableDataStream is)
            throws IOException {
        int num = is.readUnsignedShort();
        List<Annotation> annotations = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            annotations.add(readAnnotation(owner, is));
        }
        return new ParameterAnnotations(annotations);
    }

    private static Constant readConst(ConstantType constType, IndexableDataStream is) throws IOException {
        // Braces in switches are ugly, but having variable name pass-through sucks.
        switch(constType) {
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
                return new ConstUTF8(is.readUTF());
            }
            case CLASS: {
                int name = is.readUnsignedShort();
                return new ConstClass(name);
            }
            case MODULE: {
                int name = is.readUnsignedShort();
                return new ConstModule(name);
            }
            case PACKAGE: {
                int name = is.readUnsignedShort();
                return new ConstPackage(name);
            }
            case FIELD: {
                int clazz = is.readUnsignedShort();
                int nameType = is.readUnsignedShort();
                return new ConstField(clazz, nameType);
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
                int bootstrap = is.readUnsignedShort();
                int nameType = is.readUnsignedShort();
                return new ConstInvokeDynamic(bootstrap, nameType);
            }
            case DYNAMIC: {
                int bootstrap = is.readUnsignedShort();
                int nameType = is.readUnsignedShort();
                return new ConstDynamic(bootstrap, nameType);
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

    private static Attribute readStackMapTable(int nameIndex, IndexableDataStream is) throws IOException {
        int entries = is.readUnsignedShort();
        List<Frame> frames = new ArrayList<>();
        for (int i = 0; i < entries; i++) {
            int type = is.read();
            if (type >= 0 && type <= 63) {
                // same_frame
                frames.add(new Frame.Same(type));
            } else if (type >= 64 && type <= 127) {
                // same_locals_1_stack_item_frame
                frames.add(new Frame.SameLocals1StackItem(type, readVerificationType(is)));
            } else if (type == 247) {
                // same_locals_1_stack_item_frame_extended
                int offset = is.readUnsignedShort();
                frames.add(new Frame.SameLocals1StackItemExtended(offset, readVerificationType(is)));
            } else if (type >= 248 && type <= 250) {
                // chop_frame
                int offset = is.readUnsignedShort();
                frames.add(new Frame.Chop(type, offset));
            } else if (type == 251) {
                // same_frame_extended
                int offset = is.readUnsignedShort();
                frames.add(new Frame.SameExtended(offset));
            } else if (type >= 252 && type <= 254) {
                // append_frame
                int offset = is.readUnsignedShort();
                List<VerificationType> locals = new ArrayList<>();
                int size = type - 251;
                for (int j = 0; j < size; j++) {
                    locals.add(readVerificationType(is));
                }
                frames.add(new Frame.Append(type,offset, locals));
            } else if (type == 255) {
                // full_frame
                int offset = is.readUnsignedShort();
                int numLocals = is.readUnsignedShort();
                List<VerificationType> locals = new ArrayList<>();
                for (int j = 0; j < numLocals; j++) {
                    locals.add(readVerificationType(is));
                }
                int numStack = is.readUnsignedShort();
                List<VerificationType> stack = new ArrayList<>();
                for (int j = 0; j < numStack; j++) {
                    stack.add(readVerificationType(is));
                }
                frames.add(new Frame.Full(offset, locals, stack));
            } else {
                throw new RuntimeException("Invalid frame type: " + type);
            }
        }
        return new AttributeStackMapTable(nameIndex, frames);
    }

    private static VerificationType readVerificationType(IndexableDataStream is) throws IOException {
        int tag = is.read();
        switch (tag) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            return new VerificationType(tag);
        case 7:
            return new VerificationType.ObjectVariable(is.readUnsignedShort());
        case 8:
            return new VerificationType.UninitializedVariable(is.readUnsignedShort());
        }
        return null;
    }
}