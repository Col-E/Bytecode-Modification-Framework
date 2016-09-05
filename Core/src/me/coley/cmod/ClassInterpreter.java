package me.coley.cmod;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

import com.google.common.collect.Lists;

import me.coley.cmod.attribute.Attribute;
import me.coley.cmod.attribute.AttributeType;
import me.coley.cmod.attribute.annotation.Annotation;
import me.coley.cmod.attribute.annotation.AttributeAnnotations;
import me.coley.cmod.attribute.annotation.AttributeParameterAnnotations;
import me.coley.cmod.attribute.annotation.ElementPair;
import me.coley.cmod.attribute.annotation.ParameterAnnotations;
import me.coley.cmod.attribute.field.AttributeConstantValue;
import me.coley.cmod.attribute.method.AttributeCode;
import me.coley.cmod.attribute.method.AttributeLineNumberTable;
import me.coley.cmod.attribute.method.AttributeLocalVariableTable;
import me.coley.cmod.attribute.method.LineNumberTable;
import me.coley.cmod.attribute.method.LocalVariableTable;
import me.coley.cmod.attribute.method.MethodException;
import me.coley.cmod.attribute.method.OpcodeListData_TEMP;
import me.coley.cmod.consts.*;
import me.coley.cmod.exception.InvalidClassException;
import me.coley.cmod.io.StreamUtil;

@SuppressWarnings("rawtypes")
class ClassInterpreter {

	public static ClassNode getNode(byte[] data) throws InvalidClassException, IOException {
		DataInputStream is = StreamUtil.fromBytes(data);
		if (is.readInt() != 0xCAFEBABE) {
			throw new InvalidClassException();
		}
		// Create the node
		ClassNode node = new ClassNode();
		// Read version information
		node.minor = is.readUnsignedShort();
		node.major = is.readUnsignedShort();
		// Read the constant pool
		int poolIndex = 1;
		int poolLength = is.readUnsignedShort();
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
		int interfaceIndex = 0;
		int interfaceLength = is.readUnsignedShort();
		while (interfaceIndex < interfaceLength) {
			node.addInterfaceIndex(is.readUnsignedShort());
			interfaceIndex++;
		}
		// Read fields
		int fieldIndex = 0;
		int fieldLength = is.readUnsignedShort();
		while (fieldIndex < fieldLength) {
			FieldNode field = readField(node, is);
			node.addField(field);
			fieldIndex++;
		}
		// Read methods
		int methodIndex = 0;
		int methodLength = is.readUnsignedShort();
		while (methodIndex < methodLength) {
			MethodNode method = readMethod(node, is);
			node.addMethod(method);
			methodIndex++;
		}
		// Read class attributes
		int attribsIndex = 0;
		int attribsLength = is.readUnsignedShort();
		while (attribsIndex < attribsLength) {
			Attribute attribute = readAttribute(node, is);
			node.addAttribute(attribute);
			attribsIndex++;
		}
		return node;
	}

	private static FieldNode readField(ClassNode owner, DataInputStream is) throws IOException {
		FieldNode field = new FieldNode(owner);
		field.access = is.readUnsignedShort();
		field.name = is.readUnsignedShort();
		field.desc = is.readUnsignedShort();
		int attribsIndex = 0;
		int attribsLength = is.readUnsignedShort();
		while (attribsIndex < attribsLength) {
			Attribute attribute = readAttribute(owner, is);
			field.addAttribute(attribute);
			attribsIndex++;
		}
		return field;
	}

	private static MethodNode readMethod(ClassNode owner, DataInputStream is) throws IOException {
		MethodNode method = new MethodNode(owner);
		method.access = is.readUnsignedShort();
		method.name = is.readUnsignedShort();
		method.desc = is.readUnsignedShort();
		int attribsIndex = 0;
		int attribsLength = is.readUnsignedShort();
		while (attribsIndex < attribsLength) {
			Attribute attribute = readAttribute(owner, is);
			method.addAttribute(attribute);
			attribsIndex++;
		}
		return method;
	}

	private static Attribute readAttribute(ClassNode owner, DataInputStream is) throws IOException {
		// https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7
		int nameIndex = is.readUnsignedShort();
		int length = is.readInt();
		String name = owner.getConst(nameIndex).value.toString();

		System.out.println("Name & len remaining: " + nameIndex + ":" + name + ":" + length);
		AttributeType attributeType = AttributeType.fromName(name);
		switch (attributeType) {
		case ANNOTATION_DEFAULT: {
			break;
		}
		case BOOTSTRAP_METHOS: {
			break;
		}
		case CODE: {
			int maxStack = is.readUnsignedShort();
			int maxLocals = is.readUnsignedShort();
			int codeLength = is.readInt();
			byte[] code = new byte[codeLength];
			is.read(code);
			OpcodeListData_TEMP codeData = new OpcodeListData_TEMP();
			codeData.data = code;
			int exceptionIndex = 0;
			int exceptionLength = is.readShort();
			while (exceptionIndex < exceptionLength) {
				MethodException mexeption = new MethodException();
				int rangeStart = is.readUnsignedShort();
				int rangeEnd = is.readUnsignedShort();
				int rangeHandler = is.readUnsignedShort();
				int catchType = is.readUnsignedShort();
				mexeption.start = rangeStart;
				mexeption.end = rangeEnd;
				mexeption.handler = rangeHandler;
				mexeption.type = catchType;
				codeData.addException(mexeption);
				exceptionIndex++;
			}
			List<Attribute> attributes = Lists.newArrayList();
			int attributeIndex = 0;
			int attributeLength = is.readShort();
			while (attributeIndex < attributeLength) {
				Attribute attribute = readAttribute(owner, is);
				attributes.add(attribute);
				attributeIndex++;
			}
			return new AttributeCode(nameIndex, maxStack, maxLocals, codeData, attributes);
		}
		case CONSTANT_VALUE: {
			int value = is.readUnsignedShort();
			return new AttributeConstantValue(nameIndex, value);
		}
		case DEPRECATED: {
			break;
		}
		case ENCLOSING_METHOD: {
			break;
		}
		case EXCEPTIONS: {
			break;
		}
		case INNER_CLASSES: {
			break;
		}
		case LINE_NUMBER_TABLE: {
			LineNumberTable table = new LineNumberTable();
			int tableIndex = 0;
			int tableLength = is.readUnsignedShort();
			while (tableIndex < tableLength) {
				int startPC = is.readUnsignedShort();
				int lineNumber = is.readUnsignedShort();
				table.add(startPC, lineNumber);
				tableIndex++;
			}
			return new AttributeLineNumberTable(nameIndex, table);
		}
		case LOCAL_VARIABLE_TYPE_TABLE: {
			int tableIndex = 0;
			int tableLength = is.readUnsignedShort();
			while (tableIndex < tableLength) {
				tableIndex++;
			}
			break;
		}
		case LOCAL_VARIABLE_TABLE: {
			LocalVariableTable locals = new LocalVariableTable();
			int tableIndex = 0;
			int tableLength = is.readUnsignedShort();
			while (tableIndex < tableLength) {
				int lstartPC = is.readUnsignedShort();
				int llength = is.readUnsignedShort();
				int lname = is.readUnsignedShort();
				int ldesc = is.readUnsignedShort();
				int lindex = is.readUnsignedShort();
				locals.add(lstartPC, llength, lname, ldesc, lindex);
				tableIndex++;
			}
			return new AttributeLocalVariableTable(nameIndex, locals);
		}
			// TODO: Check if any of this annotation parsing is right.
			// The structure in the documentation is kinda complex.
			// Not to mention it totally ignored what ElementPairs are.
			//
			// https://docs.oracle.com/javase/specs/jvms/se7/html/jvms-4.html#jvms-4.7.16
		case RUNTIME_VISIBLE_PARAMETER_ANNOTATIONS:
		case RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS: {
			boolean invis = attributeType == AttributeType.RUNTIME_INVISIBLE_PARAMETER_ANNOTATIONS;
			int paramAnnos = is.readUnsignedByte();
			List<ParameterAnnotations> listParamAnnos = Lists.newArrayList();
			for (int i = 0; i < paramAnnos; i++) {
				ParameterAnnotations pa = new ParameterAnnotations();
				int annos = is.readUnsignedShort();
				for (int p = 0; p < annos; p++) {
					pa.addAnnotation(readAnnotation(is));
				}
				listParamAnnos.add(pa);
			}
			System.out.println("<> Tried parsing annotation: " + attributeType.name() + " Did it work?");
			return new AttributeParameterAnnotations(nameIndex, invis, listParamAnnos);
		}
		case RUNTIME_VISIBLE_ANNOTATIONS:
		case RUNTIME_INVISIBLE_ANNOTATIONS: {
			boolean invis = attributeType == AttributeType.RUNTIME_INVISIBLE_ANNOTATIONS;
			int annos = is.readUnsignedShort();
			List<Annotation> annotations = Lists.newArrayList();
			for (int i = 0; i < annos; i++) {
				annotations.add(readAnnotation(is));
			}
			System.out.println("<> Tried parsing annotation: " + attributeType.name() + " Did it work?");
			return new AttributeAnnotations(nameIndex, invis, annotations);
		}
		case SIGNATURE: {
			break;
		}
		case SOURCE_DEBUG_EXTENSION: {
			break;
		}
		case SOURCE_FILE: {
			break;
		}
		case STACK_MAP_TABLE: {
			break;
		}
		case SYNTHETIC: {
			break;
		}
		default:
			break;
		}
		throw new RuntimeException("Unhandled attribute! " + attributeType);
	}

	private static Annotation readAnnotation(DataInputStream is) throws IOException {
		int type = is.readUnsignedShort();
		int numPairs = is.readUnsignedShort();
		Annotation anno = new Annotation(type);
		for (int p = 0; p < numPairs; p++) {
			int pairElementName = is.readUnsignedShort();
			int value = is.readUnsignedShort();
			anno.addPair(new ElementPair(pairElementName, value));
		}
		return anno;
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