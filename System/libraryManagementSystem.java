import java.util.*;
import java.io.*;

class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public String getTitle() { return title; }

    public String getAuthor() { return author; }

    public String getIsbn() { return isbn; }

    public boolean isAvailable() { return isAvailable; }

    public void markAsBorrowed() { isAvailable = false; }

    public void markAsReturned() { isAvailable = true; }

    public void displayInfo() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("ISBN: " + isbn);
        System.out.println("Available: " + (isAvailable ? "Yes" : "No"));
        System.out.println("--------------");
    }

    @Override
    public String toString() {
        return title + "," + author + "," + isbn + "," + isAvailable;
    }

    public static Book fromString(String line) {
        String[] parts = line.split(",");
        Book book = new Book(parts[0], parts[1], parts[2]);
        if (parts.length > 3 && parts[3].equals("false")) {
            book.markAsBorrowed();
        }
        return book;
    }
}

class Member {
    private String name;
    private String memberId;
    private ArrayList<Book> borrowedBooks;

    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.markAsBorrowed();
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book is currently unavailable.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.markAsReturned();
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("This book was not borrowed by the member.");
        }
    }

    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
        } else {
            for (Book book : borrowedBooks) {
                book.displayInfo();
            }
        }
    }
}

class Library {
    private ArrayList<Book> books;
    private ArrayList<Member> members;

    public Library() {
        books = new ArrayList<>();
        members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
        System.out.println("Book added.");
    }

    public void registerMember(Member member) {
        members.add(member);
        System.out.println("Member registered.");
    }

    public Book findBookByISBN(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) return book;
        }
        return null;
    }

    public ArrayList<Book> searchBooksByTitle(String title) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase()))
                results.add(book);
        }
        return results;
    }

    public ArrayList<Book> searchBooksByAuthor(String author) {
        ArrayList<Book> results = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                results.add(book);
        }
        return results;
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            for (Book book : books) {
                book.displayInfo();
            }
        }
    }

    public void saveDataToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Book book : books) {
                bw.write(book.toString());
                bw.newLine();
            }
            System.out.println("Data saved.");
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    public void loadBooksFromFile(String filename) {
        books.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                books.add(Book.fromString(line));
            }
            System.out.println("Data loaded.");
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public Member getMemberById(String id) {
        for (Member member : members) {
            if (member.getMemberId().equals(id)) return member;
        }
        return null;
    }
}

public class libraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        int choice;

        do {
            displayMenu();
            choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Title: ");
                    String title = sc.nextLine();
                    System.out.print("Author: ");
                    String author = sc.nextLine();
                    System.out.print("ISBN: ");
                    String isbn = sc.nextLine();
                    library.addBook(new Book(title, author, isbn));
                    break;

                case 2:
                    System.out.print("Member name: ");
                    String name = sc.nextLine();
                    System.out.print("Member ID: ");
                    String memberId = sc.nextLine();
                    library.registerMember(new Member(name, memberId));
                    break;

                case 3:
                    System.out.print("Member ID: ");
                    String memId = sc.nextLine();
                    Member borrower = library.getMemberById(memId);
                    if (borrower != null) {
                        System.out.print("Book ISBN: ");
                        String bookIsbn = sc.nextLine();
                        Book book = library.findBookByISBN(bookIsbn);
                        if (book != null) {
                            borrower.borrowBook(book);
                        } else {
                            System.out.println("Book not found.");
                        }
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;

                case 4:
                    System.out.print("Member ID: ");
                    String returnerId = sc.nextLine();
                    Member returner = library.getMemberById(returnerId);
                    if (returner != null) {
                        System.out.print("Book ISBN: ");
                        String returnIsbn = sc.nextLine();
                        Book book = library.findBookByISBN(returnIsbn);
                        if (book != null) {
                            returner.returnBook(book);
                        } else {
                            System.out.println("Book not found.");
                        }
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;

                case 5:
                    System.out.print("Search title: ");
                    String t = sc.nextLine();
                    for (Book b : library.searchBooksByTitle(t)) {
                        b.displayInfo();
                    }
                    break;

                case 6:
                    System.out.print("Search author: ");
                    String a = sc.nextLine();
                    for (Book b : library.searchBooksByAuthor(a)) {
                        b.displayInfo();
                    }
                    break;

                case 7:
                    library.displayAllBooks();
                    break;

                case 8:
                    System.out.print("Member ID: ");
                    String mId = sc.nextLine();
                    Member m = library.getMemberById(mId);
                    if (m != null) {
                        m.displayBorrowedBooks();
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;

                case 9:
                    library.saveDataToFile("library_books.txt");
                    break;

                case 10:
                    library.loadBooksFromFile("library_books.txt");
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }

        } while (choice != 0);

        sc.close();
    }

    public static void displayMenu() {
        System.out.println("\n==== Library Menu ====");
        System.out.println("1. Add a new book");
        System.out.println("2. Register a new member");
        System.out.println("3. Borrow a book");
        System.out.println("4. Return a book");
        System.out.println("5. Search book by title");
        System.out.println("6. Search book by author");
        System.out.println("7. Display all books");
        System.out.println("8. Display borrowed books of a member");
        System.out.println("9. Save data to file");
        System.out.println("10. Load data from file");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
}
