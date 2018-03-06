package reflectasm;

import com.esotericsoftware.reflectasm.MethodAccess;

/**
 * @author dale.wdl@alibaba-inc.com
 * @date 2018/3/5
 */
public class ReflectAsmMain {

    public static void main(String[] args) {

        SomeClass someClass = new SomeClass();
        someClass.age = 100;
        someClass.name = "wudale";
        MethodAccess methodAccess = MethodAccess.get(SomeClass.class);
        String getString = (String)methodAccess.invoke(someClass, "getString");
        System.out.println(getString);
    }

    public static class SomeClass {
        private String name;

        private int age;

        public String getString() {
            return name + "'s age is " + age;
        }

    }
}
