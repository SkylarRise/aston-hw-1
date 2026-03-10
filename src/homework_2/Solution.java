package homework_2;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


public class Solution {

    static final String path = "src/homework_2/student_books";
    static final int indexStudentCode = 0;
    static final int indexStudentName = 1;
    static final int indexBookCode = 2;
    static final int indexBookName = 3;
    static final int indexBookAuthor = 4;
    static final int indexBookPublished = 5;
    static final int indexBookPages = 6;
    static final String splitter = ";";
    static Set<Student> studentSet = new HashSet<>();
    static Map<String, Student> studentsHashMap = new HashMap<>();
    static Map<String, Book> booksHashMap = new HashMap<>();

    /**
     * Выполняет обработку студентов и их книг при помощи Stream API.
     * <p>
     * Последовательно:
     * 1. Выводит каждого студента в консоль.
     * 2. Получает все книги студентов.
     * 3. Объединяет их в один поток.
     * 4. Сортирует книги по количеству страниц.
     * 5. Убирает дубликаты.
     * 6. Оставляет книги, выпущенные после 2000 года.
     * 7. Ограничивает поток первыми тремя книгами.
     * 8. Получает годы выпуска книг.
     * 9. Возвращает Optional года выпуска при помощи short-circuit операции.
     * 10. Выводит найденный год или сообщение об отсутствии книг.
     */
    public static void showResult() {

        studentSet.stream()
                .peek(System.out::println)
                .flatMap(Student::books)
                .sorted()
                .distinct()
                .filter(book -> book.getPublished() > 2000)
                .limit(3)
                .map(Book::getPublished)
                .findFirst()
                .ifPresentOrElse(
                        year -> System.out.println("Год выпуска: " + year),
                        () -> System.out.println("Такая книга отсутствует")
                );
    }

    /**
     * Возвращает студента по коду.
     * Если студент отсутствует в коллекции — создаёт и сохраняет нового.
     */
    public static Student getOrCreateStudent(String[] data) {
        String codeStudent = data[indexStudentCode];
        String nameStudent = data[indexStudentName];

        return studentsHashMap.computeIfAbsent(codeStudent, k -> new Student(k, nameStudent));

    }

    /**
     * Безопасно преобразует строку в целое число.
     *
     * Если строка равна null, пустая или не может быть преобразована в число,
     * метод возвращает null вместо выброса исключения.
     */
    private static Integer parseIntSafe(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Возвращает книгу по коду.
     * Если книга отсутствует в коллекции — создаёт и сохраняет новую.
     * Если данные книги некорректны (не удалось распарсить числа) — возвращает null.
     */
    public static Book getOrCreateBook(String[] data) {

        Integer pages = parseIntSafe(data[indexBookPages]);
        Integer published = parseIntSafe(data[indexBookPublished]);

        if (pages == null || published == null) {
            return null;
        }

        String codeBook = data[indexBookCode];
        String nameBook = data[indexBookName];
        String author = data[indexBookAuthor];

        return booksHashMap.computeIfAbsent(codeBook,
                k -> new Book(k, nameBook, author, published, pages));
    }

    /**
     * Обрабатывает строку из файла:
     * извлекает данные студента и книги,
     * создаёт или получает существующие объекты
     * и связывает студента с книгой.
     */
    public static Student processStudentBookLine(String line) {

        String[] data = line.split(splitter, 7);

        Student student = getOrCreateStudent(data);
        Book book = getOrCreateBook(data);

        if (book == null) {
            return null;
        }

        student.getBookList().add(book);

        return student;

    }

    /**
     * Метод проверяет наличие файла, в котором содержаться данные студентов и их книг.
     */
    public static void checkFile() throws FileNotFoundException {

        File file = new File(path);

        if (!file.exists()) {
            String errorMes = String.format("Файл по пути %s не найден.", path);
            throw new FileNotFoundException(errorMes);
        }
    }

    /**
     * Читает файл со студентами и книгами,
     * формирует объекты и заполняет коллекцию студентов.
     */
    public static void readFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {

                Student student = processStudentBookLine(line);

                if (student != null) {
                    studentSet.add(student);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Точка входа программы.
     * Проверяет наличие файла, читает данные и выводит результат обработки.
     */
    public static void main(String[] args) throws FileNotFoundException {

        // 1. Проверим существование файла со студентами.
        checkFile();
        // 2. Прочитаем и обработаем файл.
        readFile();
        // 3. Выводим результат.
        showResult();

    }
}



