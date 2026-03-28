package p1;
import java.util.*;
import java.io.*;

class Book {
    int id;
    String title;
    String author;
    boolean issued;

    Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = false;
    }
}

class Node {
    String title;
    Node next;

    Node(String title) {
        this.title = title;
        next = null;
    }
}

public class LibraryManagementSystem {

    static Scanner sc = new Scanner(System.in);

    
    static Book books[] = new Book[100];
    static int count = 0;

    
    static Node head = null;

    
    static String stack[] = new String[100];
    static int top = -1;

    
    static String queue[] = new String[100];
    static int front = 0, rear = -1;

    
    static void writeToFile(String msg) {
        try (FileWriter fw = new FileWriter("library_output.txt", true)) {
            fw.write(msg + "\n");
        } catch (Exception e) {
            System.out.println("File error");
        }
    }

  
    static void pushStack(String msg) {
        if (top < stack.length - 1) {
            stack[++top] = msg;
        }
    }

    
    static void addBook() {

        if (count >= books.length) {
            System.out.println("Library is full.");
            return;
        }

        try {

            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();
            sc.nextLine();

            for (int i = 0; i < count; i++) {
                if (books[i].id == id) {
                    System.out.println("Book ID already exists.");
                    return;
                }
            }

            System.out.print("Enter Title: ");
            String title = sc.nextLine();

            System.out.print("Enter Author: ");
            String author = sc.nextLine();

            books[count++] = new Book(id, title, author);

            String msg = "Book Added: " + title;
            System.out.println(msg);

            writeToFile(msg);
            pushStack(msg);

        } catch (InputMismatchException e) {
            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    
    static void displayBooks() {

        if (count == 0) {
            System.out.println("No books available.");
            return;
        }

        for (int i = 0; i < count; i++) {

            System.out.println("ID: " + books[i].id +
                    " | Title: " + books[i].title +
                    " | Author: " + books[i].author +
                    " | Issued: " + books[i].issued);
        }
    }

   
    static void searchBook() {

        try {

            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();

            for (int i = 0; i < count; i++) {

                if (books[i].id == id) {

                    System.out.println("Book Found: " +
                            books[i].title +
                            " | Author: " +
                            books[i].author +
                            " | Issued: " +
                            books[i].issued);
                    return;
                }
            }

            System.out.println("Book not found.");

        } catch (InputMismatchException e) {

            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

   
    static void addIssuedBook(String title) {

        Node newNode = new Node(title);

        if (head == null) {
            head = newNode;
        } else {

            Node temp = head;

            while (temp.next != null)
                temp = temp.next;

            temp.next = newNode;
        }
    }

    
    static void removeIssuedBook(String title) {

        Node temp = head;
        Node prev = null;

        while (temp != null) {

            if (temp.title.equals(title)) {

                if (prev == null)
                    head = temp.next;
                else
                    prev.next = temp.next;

                return;
            }

            prev = temp;
            temp = temp.next;
        }
    }

    
    static void issueBook() {

        try {

            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();

            for (int i = 0; i < count; i++) {

                if (books[i].id == id) {

                    if (books[i].issued) {
                        System.out.println("Book already issued.");
                        return;
                    }

                    books[i].issued = true;

                    addIssuedBook(books[i].title);

                    String msg = "Book Issued: " + books[i].title;

                    System.out.println(msg);
                    writeToFile(msg);
                    pushStack(msg);

                    return;
                }
            }

            System.out.println("Book not found.");

        } catch (InputMismatchException e) {

            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    
    static void returnBook() {

        try {

            System.out.print("Enter Book ID: ");
            int id = sc.nextInt();

            for (int i = 0; i < count; i++) {

                if (books[i].id == id) {

                    if (!books[i].issued) {
                        System.out.println("Book was not issued.");
                        return;
                    }

                    books[i].issued = false;

                    removeIssuedBook(books[i].title);

                    String msg = "Book Returned: " + books[i].title;

                    System.out.println(msg);
                    writeToFile(msg);
                    pushStack(msg);

                    return;
                }
            }

            System.out.println("Book not found.");

        } catch (InputMismatchException e) {

            System.out.println("Invalid input.");
            sc.nextLine();
        }
    }

    
    static void reserveBook() {

        if (rear >= queue.length - 1) {
            System.out.println("Queue full.");
            return;
        }

        sc.nextLine();

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        queue[++rear] = name;

        System.out.println("Reservation added for: " + name);
    }

   
    static void displayQueue() {

        if (front > rear) {

            System.out.println("No reservations.");
            return;
        }

        System.out.println("Reservation Queue:");

        for (int i = front; i <= rear; i++)
            System.out.println(queue[i]);
    }

    
    static void undoOperation() {

        if (top == -1) {
            System.out.println("Nothing to undo.");
            return;
        }

        String last = stack[top--];

        System.out.println("Undo: " + last);
    }

    
    static void sortBooks() {

        if (count == 0) {
            System.out.println("No books available.");
            return;
        }

        
        for (int i = 0; i < count - 1; i++) {

            for (int j = 0; j < count - i - 1; j++) {

                if (books[j].title.compareToIgnoreCase(books[j + 1].title) > 0) {

                    Book temp = books[j];
                    books[j] = books[j + 1];
                    books[j + 1] = temp;
                }
            }
        }

        System.out.println("\nSorted Books List:");

        for (int i = 0; i < count; i++) {

            System.out.println(
                    "ID: " + books[i].id +
                    " | Title: " + books[i].title +
                    " | Author: " + books[i].author +
                    " | Issued: " + books[i].issued);
        }
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== Library Management System =====");
            System.out.println("1. Add Book");
            System.out.println("2. Display Books");
            System.out.println("3. Search Book");
            System.out.println("4. Issue Book");
            System.out.println("5. Return Book");
            System.out.println("6. Reserve Book");
            System.out.println("7. Display Reservations");
            System.out.println("8. Undo");
            System.out.println("9. Sort Books");
            System.out.println("10. Exit");

            System.out.print("Enter choice: ");

            try {

                int ch = sc.nextInt();

                switch (ch) {

                    case 1: addBook(); break;
                    case 2: displayBooks(); break;
                    case 3: searchBook(); break;
                    case 4: issueBook(); break;
                    case 5: returnBook(); break;
                    case 6: reserveBook(); break;
                    case 7: displayQueue(); break;
                    case 8: undoOperation(); break;
                    case 9: sortBooks(); break;
                    case 10: System.out.println("Exiting...");
                    System.exit(0);
                    default:
                    	System.out.println("Invalid choice.");
                }
            }
            catch(InputMismatchException e)
            {
            	System.out.println("Invalid input.");
            	sc.nextLine();
            }
        }
    }
}
                    
                    