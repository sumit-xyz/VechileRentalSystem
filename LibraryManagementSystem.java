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
        System.out.println("-----------------------");
    }

    @Override
    public String toString() {
        return "\"" + title + "\",\"" + author + "\",\"" + isbn + "\"," + isAvailable;
    }

    public static Book fromString(String line) {
        try {
            String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
            if (parts.length < 4) return null;

            String title = parts[0].replaceAll("^\"|\"$", "");
            String author = parts[1].replaceAll("^\"|\"$", "");
            String isbn = parts[2].replaceAll("^\"|\"$", "");
            boolean available = Boolean.parseBoolean(parts[3]);

            Book book = new Book(title, author, isbn);
            if (!available) book.markAsBorrowed();
            return book;
        } catch (Exception e) {
            return null;
        }
    }
}

class Member {
    private String name;
    private String memberId;
    private ArrayList<Book> borrowedBooks;

    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        borrowedBooks = new ArrayList<>();
    }

    public String getMemberId() { return memberId; }

    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            borrowedBooks.add(book);
            book.markAsBorrowed();
            System.out.println("Book borrowed successfully.");
        } else {
            System.out.println("Book is not available.");
        }
    }

    public void returnBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.markAsReturned();
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("This book was not borrowed by this member.");
        }
    }

    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
        } else {
            for (Book b : borrowedBooks) {
                b.displayInfo();
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
        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) return b;
        }
        return null;
    }

    public ArrayList<Book> searchBooksByTitle(String title) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getTitle().toLowerCase().contains(title.toLowerCase()))
                result.add(b);
        }
        return result;
    }

    public ArrayList<Book> searchBooksByAuthor(String author) {
        ArrayList<Book> result = new ArrayList<>();
        for (Book b : books) {
            if (b.getAuthor().toLowerCase().contains(author.toLowerCase()))
                result.add(b);
        }
        return result;
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            for (Book b : books) {
                b.displayInfo();
            }
        }
    }

    public void saveDataToFile(String filename) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Book b : books) {
                bw.write(b.toString());
                bw.newLine();
            }
            System.out.println("Data saved to CSV.");
        } catch (IOException e) {
            System.out.println("Error saving: " + e.getMessage());
        }
    }

    public void loadBooksFromFile(String filename) {
        books.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                Book b = Book.fromString(line);
                if (b != null) books.add(b);
            }
            System.out.println("Data loaded from CSV.");
        } catch (IOException e) {
            System.out.println("Error loading: " + e.getMessage());
        }
    }

    public Member getMemberById(String id) {
        for (Member m : members) {
            if (m.getMemberId().equals(id)) return m;
        }
        return null;
    }
}

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();
        int choice;

        do {
            displayMenu();
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input. Enter a number.");
                sc.next(); // discard invalid input
            }
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

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
                    System.out.print("Member Name: ");
                    String name = sc.nextLine();
                    System.out.print("Member ID: ");
                    String memberId = sc.nextLine();
                    library.registerMember(new Member(name, memberId));
                    break;

                case 3:
                    System.out.print("Member ID: ");
                    String borrowerId = sc.nextLine();
                    Member borrower = library.getMemberById(borrowerId);
                    if (borrower != null) {
                        System.out.print("Book ISBN: ");
                        String borrowIsbn = sc.nextLine();
                        Book book = library.findBookByISBN(borrowIsbn);
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
                    String searchTitle = sc.nextLine();
                    for (Book b : library.searchBooksByTitle(searchTitle)) {
                        b.displayInfo();
                    }
                    break;

                case 6:
                    System.out.print("Search author: ");
                    String searchAuthor = sc.nextLine();
                    for (Book b : library.searchBooksByAuthor(searchAuthor)) {
                        b.displayInfo();
                    }
                    break;

                case 7:
                    library.displayAllBooks();
                    break;

                case 8:
                    System.out.print("Member ID: ");
                    String memId = sc.nextLine();
                    Member mem = library.getMemberById(memId);
                    if (mem != null) {
                        mem.displayBorrowedBooks();
                    } else {
                        System.out.println("Member not found.");
                    }
                    break;

                case 9:
                    library.saveDataToFile("library_books.csv");
                    break;

                case 10:
                    library.loadBooksFromFile("library_books.csv");
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
        System.out.println("\n=== Library Menu ===");
        System.out.println("1. Add new book");
        System.out.println("2. Register new member");
        System.out.println("3. Borrow a book");
        System.out.println("4. Return a book");
        System.out.println("5. Search book by title");
        System.out.println("6. Search book by author");
        System.out.println("7. Display all books");
        System.out.println("8. Display borrowed books of a member");
        System.out.println("9. Save data to CSV file");
        System.out.println("10. Load data from CSV file");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }
}

