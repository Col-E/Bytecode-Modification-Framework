package io.github.bmf.signature;

import java.util.HashMap;
import java.util.Map;

import io.github.bmf.type.Type;
import io.github.bmf.util.Box;
import io.github.bmf.util.ImmutableBox;

/*
//@formatter:off
<T:Ljava/lang/Object;Z:Ljava/lang/Object;>Ljava/lang/Object;
        V : Lobtest/Generics<TT;TZ;>;
    M : (TT;)TT;
        V : Lobtest/Generics<TT;TZ;>;
        V : TT;
    M : (TZ;)TZ;
        V : Lobtest/Generics<TT;TZ;>;
        V : TZ;
    M : <V:Ljava/lang/Object;>(TV;)TV;
        V : TV;
        V : Lobtest/Generics<Lobtest/InterfaceA;Lobtest/InterfaceB;>;
    F : TT;
    F : TZ;
//@formatter:on
 */
public class Signature {

    public static void main(String[] args) {
        System.out.println("======================");
        try {
           // method("<V:Ljava/lang/Object;>(TV;)TV;").toSignature();
            method("<V:Ljava/lang/Object;K:Ljava/lang/Object;>(TV;TK;)TV;").toSignature();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("======================");
    }

    public static Signature method(String sig) {
        // Independent generics
        // <V:Ljava/lang/Object;>(TV;)TV;
        // <V:Ljava/lang/Object;K:Ljava/lang/Object;>(TV;TK;)TV;
        //
        // Class-defined generics
        // (TZ;)TZ;
        // (Ljava/util/List<TZ;>;TT;I)V
        // ()Ljava/util/Map<TT;TZ;>;
        //
        // Nightmare-inducing cases
        // ()Ljava/util/Map<Ljava/lang/String;Ljava/lang/Boolean;>;
        // ()Ljava/util/Map<Ljava/lang/String;Ljava/util/Map<Ljava/lang/Integer;Ljava/lang/Float;>;>;
        //
        Map<String, Box<String>> typeMap = null;
        // Independent generic
        if (sig.startsWith("<")) {
            int end = sig.indexOf(">");
            typeMap = new HashMap<>();
            String sub = sig.substring(1, end - 1);
            String split[] = sub.split(";");
            for (String s : split) {
                String split2[] = s.split(":");
                // TODO: Replace with mapping
                typeMap.put(split2[0], new ImmutableBox<String>(split2[1]));
            }
            sig = sig.substring(end + 1);
        }

        return null;
    }

    public static Signature variable(String sig){
        // Type with the generics
        // Lobtest/Generics<TT;TZ;>;
        // Ljava/util/List<Ljava/lang/String;>;
        // TT;
        // Plain generic as the type
        
        return null;
    }

    public String toSignature() {
        return null;
    }
}
