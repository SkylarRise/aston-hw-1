package homework_2;

import lombok.Data;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;


@Data
public class Student {
    private String code;
    private String name;
    private List<Book> bookList = new ArrayList<>();

    public Student(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public Stream<Book> books() {
        return bookList.stream();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student student)) return false;
        return Objects.equals(student.code, code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code);
    }
}
