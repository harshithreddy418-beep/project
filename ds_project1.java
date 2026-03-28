for (int i = 0; i < count; i++) {
            if (books[i].id == id) {

                if (!books[i].issued) {
                    System.out.println("Book was not issued.");
                    writeToFile("RETURN FAILED → Not issued");
                    return;
                }

                books[i].issued = false;

                System.out.println("Book Returned Successfully");
                writeToFile("RETURN → " + books[i].title);
                saveAllBooks();
                return;
            }
        }

        System.out.println("Book not found.");
        writeToFile("RETURN FAILED → Not found");
    }

    static void reserveBook() {
        sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        queue[++rear] = name;

        System.out.println("Reservation added for: " + name);
        writeToFile("RESERVE → " + name);
    }

    static void displayQueue() {
        if (front > rear) {
            System.out.println("No reservations.");
            writeToFile("QUEUE → Empty");
            return;
        }

        System.out.println("Reservation Queue:");

        for (int i = front; i <= rear; i++) {
            System.out.println(queue[i]);
            writeToFile("QUEUE → " + queue[i]);
        }
    }

    static void sortBooks() {
        for (int i = 0; i < count - 1; i++) {
            for (int j = 0; j < count - i - 1; j++) {
                if (books[j].title.compareTo(books[j + 1].title) > 0) {
                    Book temp = books[j];
                    books[j] = books[j + 1];
                    books[j + 1] = temp;
                }
            }
        }

        System.out.println("Books sorted successfully");
        writeToFile("SORT → Books sorted");
        saveAllBooks();
    }

    public static void main(String args[]) {
        while (true) {
            System.out.println("\n1.Add 2.Display 3.Search 4.Issue 5.Return 6.Reserve 7.Queue 8.Sort 9.Exit");

            int ch = sc.nextInt();

            switch (ch) {
                case 1: addBook(); break;
                case 2: displayBooks(); break;
                case 3: searchBook(); break;
                case 4: issueBook(); break;
                case 5: returnBook(); break;
                case 6: reserveBook(); break;
                case 7: displayQueue(); break;
                case 8: sortBooks(); break;
                case 9: System.exit(0);
                default: System.out.println("Invalid choice");
            }
        }
    }
}