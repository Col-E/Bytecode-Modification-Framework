package me.coley.bmf.mapping;

import me.coley.bmf.util.Box;

public class InnerClassMapping extends ClassMapping {
    public InnerClassMapping(Box<String> outer, String inner) {
        super(createBox(outer, inner));
    }

    private static Box<String> createBox(Box<String> outer, String inner) {
        return new InnerClassBox(outer, inner);
    }
    
   static class InnerClassBox extends Box<String>{
        private final Box<String> outer;
        private Box<String> inner;
        
        public InnerClassBox(Box<String> outer, String inner) {
            super(inner);
            this.outer = outer;
            this.inner = new Box<String>(trim(inner));
        }
        
        @Override
        public String getValue() {
            return outer.getValue() + "$" + inner.getValue();
        }
        @Override
        public void setValue(String value) {
            this.inner.setValue(trim(value));
        }

        private static String trim(String value) {
           int index = value.lastIndexOf("$");
           if (index > 0){
               value = value.substring(index + 1);
           }
            return value;
        }
    }
}
