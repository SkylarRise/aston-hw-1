package homework_1;

//Реализовать свой иммутабельный класс, который будет внутри себя содержать поле с изменяемым классом.

class MutableClass {
    String code;

    public MutableClass(String code) {
        this.code = code;
    }

    public MutableClass(MutableClass copy) {
        this.code = copy.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MutableClass{" +
                "code='" + code + '\'' +
                '}';
    }
}

final class ImmutableClass {

    private final MutableClass mutableClass;

    public ImmutableClass(MutableClass mutableClass) {
        this.mutableClass = new MutableClass(mutableClass);
    }

    public MutableClass getMutableClass() {
        return new MutableClass(mutableClass);
    }
}

public class Solution {
    public static void main(String[] args) {

        ImmutableClass immutableClas = new ImmutableClass(new MutableClass("001"));

        MutableClass mc = immutableClas.getMutableClass();
        mc.setCode("003");

        System.out.println(immutableClas.getMutableClass());
        System.out.println(mc);
    }
}
