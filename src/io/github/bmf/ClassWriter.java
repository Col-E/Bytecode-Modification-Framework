package io.github.bmf;

import io.github.bmf.attribute.Attribute;
import io.github.bmf.consts.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

@SuppressWarnings("rawtypes")
public class ClassWriter {
    public static byte[] write(ClassNode node) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream ds = new DataOutputStream(baos);
        ds.writeInt(0xCAFEBABE);
        // Write version info
        ds.writeShort(node.minor);
        ds.writeShort(node.major);
        // Write the constant pool
        for (Constant constant : node.constants) {
            ds.writeByte(constant.type.getTag());
            writeConstant(ds, constant);
        }
        // Write access & index information
        ds.writeShort(node.access);
        ds.writeShort(node.classIndex);
        ds.writeShort(node.superIndex);
        // Write interfaces
        ds.writeShort(node.interfaceIndices.size());
        for (int interfaceIndex : node.interfaceIndices) {
            ds.writeShort(interfaceIndex);
        }
        // Write fields
        ds.writeShort(node.fields.size());
        for (FieldNode field : node.fields) {
            writeMember(ds, field);
        }
        // Write methods
        ds.writeShort(node.methods.size());
        for (MethodNode method : node.methods) {
            writeMember(ds, method);
        }
        // Write attributes
        List<Attribute> attribs = node.getAttributes();
        ds.writeShort(attribs.size());
        for (Attribute attrib : attribs) {
            writeAttribute(ds, attrib);
        }
        return baos.toByteArray();
    }

    private static void writeMember(DataOutputStream ds, MemberNode member) throws IOException {
        ds.writeShort(member.access);
        ds.writeShort(member.name);
        ds.writeShort(member.desc);
        List<Attribute> attribs = member.getAttributes();
        ds.writeShort(attribs.size());
        for (Attribute attrib : attribs) {
            writeAttribute(ds, attrib);
        }
    }

    private static void writeAttribute(DataOutputStream ds, Attribute attribute) throws IOException {
        ds.writeShort(attribute.name);
        ds.writeShort(attribute.getLength());
        switch (attribute.type) {
        case ANNOTATION_DEFAULT:
            break;
        case BOOTSTRAP_METHODS:
            break;
        case CODE:
            break;
        case CONSTANT_VALUE:
            break;
        case DEPRECATED:
            break;
        case ENCLOSING_METHOD:
            break;
        case EXCEPTIONS:
            break;
        case INNER_CLASSES:
            break;
        case LINE_NUMBER_TABLE:
            break;
        case LOCAL_VARIABLE_TABLE:
            break;
        case LOCAL_VARIABLE_TYPE_TABLE:
            break;
        case RUNTIME_INVISIBLE_ANNOTATIONS:
            break;
        case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS:
            break;
        case RUNTIME_VISIBLE_ANNOTATIONS:
            break;
        case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
            break;
        case SIGNATURE:
            break;
        case SOURCE_DEBUG_EXTENSION:
            break;
        case SOURCE_FILE:
            break;
        case STACK_MAP_TABLE:
            break;
        case SYNTHETIC:
            break;
        default:
            break;

        }
    }

    private static void writeConstant(DataOutputStream ds, Constant constant) throws IOException {
        switch (constant.type) {
        case DOUBLE:
            ConstDouble constDouble = (ConstDouble) constant;
            ds.writeDouble(constDouble.value);
            break;
        case LONG:
            ConstLong constLong = (ConstLong) constant;
            ds.writeLong(constLong.value);
            break;
        case FLOAT:
            ConstFloat constFloat = (ConstFloat) constant;
            ds.writeFloat(constFloat.value);
            break;
        case INT:
            ConstInt constInt = (ConstInt) constant;
            ds.writeInt(constInt.value);
            break;
        case STRING:
            ConstString constString = (ConstString) constant;
            ds.writeShort(constString.value);
            break;
        case UTF8:
            ConstUTF8 constUTF = (ConstUTF8) constant;
            ds.writeShort(constUTF.value.getBytes().length);
            ds.write(constUTF.value.getBytes());
            break;
        case CLASS:
            ConstClass constClass = (ConstClass) constant;
            ds.writeShort(constClass.value);
            break;
        case FIELD:
            ConstField constField = (ConstField) constant;
            ds.writeShort(constField.getClassIndex());
            ds.writeShort(constField.getNameTypeIndex());
            break;
        case METHOD:
            ConstMethod constMethod = (ConstMethod) constant;
            ds.writeShort(constMethod.getClassIndex());
            ds.writeShort(constMethod.getNameTypeIndex());
            break;
        case INTERFACE_METHOD:
            ConstInterfaceMethod constInterMethod = (ConstInterfaceMethod) constant;
            ds.writeShort(constInterMethod.getClassIndex());
            ds.writeShort(constInterMethod.getNameTypeIndex());
            break;
        case INVOKEDYNAMIC:
            ConstInvokeDynamic constInvokeDynamic = (ConstInvokeDynamic) constant;
            ds.writeShort(constInvokeDynamic.getBootstrapAttribute());
            ds.writeShort(constInvokeDynamic.getNameTypeIndex());
            break;
        case METHOD_HANDLE:
            ConstMethodHandle constMethodHandle = (ConstMethodHandle) constant;
            ds.writeByte(constMethodHandle.getKind());
            ds.writeShort(constMethodHandle.getIndex());
            break;
        case METHOD_TYPE:
            ConstMethodType constMethodType = (ConstMethodType) constant;
            ds.writeShort(constMethodType.value);
            break;
        case NAME_TYPE:
            ConstNameType constNameType = (ConstNameType) constant;
            ds.writeShort(constNameType.getNameIndex());
            ds.writeShort(constNameType.getDescIndex());
            break;
        default:
            break;

        }
    }
}
