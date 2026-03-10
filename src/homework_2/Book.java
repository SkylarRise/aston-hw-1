package homework_2;

import lombok.*;

import java.util.Objects;


@Data
public class Book implements Comparable<Book> {
    private String code;
    private String name;
    private String author;
    private Integer published;
    private Integer pages;

    public Book(String code, String name, String author, Integer published, int pages) {
        this.code = code;
        this.name = name;
        this.author = author;
        this.published = published;
        this.pages = pages;
    }

    @Override
    public int compareTo(Book book) {
        return Integer.compare(pages, book.getPages());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book book)) return false;
        return Objects.equals(book.code, code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.code);
    }

}
