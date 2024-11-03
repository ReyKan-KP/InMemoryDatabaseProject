import database.*;

import java.util.Scanner;

public class Main {
    private static final Database db = new Database();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the In-Memory Database CLI!");
        while (true) {
            System.out.println("\nCommands:");
            System.out.println("1: Insert");
            System.out.println("2: Get");
            System.out.println("3: Update");
            System.out.println("4: Delete");
            System.out.println("5: Save");
            System.out.println("6: Load");
            System.out.println("7: Create Index");
            System.out.println("8: Begin Transaction");
            System.out.println("9: Commit");
            System.out.println("10: Rollback");
            System.out.println("11: Exit");

            System.out.print("Enter command number: ");
            String command = scanner.nextLine().trim();

            switch (command) {
                case "1":
                    handleInsert();
                    break;
                case "2":
                    handleGet();
                    break;
                case "3":
                    handleUpdate();
                    break;
                case "4":
                    handleDelete();
                    break;
                case "5":
                    handleSave();
                    break;
                case "6":
                    handleLoad();
                    break;
                case "7":
                    handleCreateIndex();
                    break;
                case "8":
                    db.beginTransaction();
                    break;
                case "9":
                    db.commit();
                    break;
                case "10":
                    db.rollback();
                    break;
                case "11":
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Unknown command. Try again.");
            }
        }
    }

    private static void handleInsert() {
        System.out.print("Enter table name: ");
        String tableName = scanner.nextLine().trim();
        db.createTable(tableName);

        System.out.print("Enter record ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter number of fields: ");
        int numFields = Integer.parseInt(scanner.nextLine().trim());
        DbRecord record = new DbRecord();

        for (int i = 0; i < numFields; i++) {
            System.out.print("Enter field name: ");
            String fieldName = scanner.nextLine().trim();

            System.out.print("Enter field value: ");
            String fieldValue = scanner.nextLine().trim();

            record.put(fieldName, fieldValue);
        }

        db.insertRecord(tableName, id, record);
    }

    private static void handleGet() {
        System.out.print("Enter table name: ");
        String tableName = scanner.nextLine().trim();

        System.out.print("Enter record ID: ");
        String id = scanner.nextLine().trim();

        DbRecord record = db.getRecord(tableName, id);
        System.out.println((record != null) ? record : "Record not found.");
    }

    private static void handleUpdate() {
        System.out.print("Enter table name: ");
        String tableName = scanner.nextLine().trim();

        System.out.print("Enter record ID: ");
        String id = scanner.nextLine().trim();

        System.out.print("Enter field name to update: ");
        String fieldName = scanner.nextLine().trim();

        System.out.print("Enter new field value: ");
        String fieldValue = scanner.nextLine().trim();

        DbRecord record = db.getRecord(tableName, id);
        if (record != null) {
            record.put(fieldName, fieldValue);
            System.out.println("Record updated.");
        } else {
            System.out.println("Record not found.");
        }
    }

    private static void handleDelete() {
        System.out.print("Enter table name: ");
        String tableName = scanner.nextLine().trim();

        System.out.print("Enter record ID: ");
        String id = scanner.nextLine().trim();

        db.deleteRecord(tableName, id);
    }

    private static void handleSave() {
        System.out.print("Enter filename: ");
        String filename = scanner.nextLine().trim();
        db.saveToFile(filename);
    }

    private static void handleLoad() {
        System.out.print("Enter filename: ");
        String filename = scanner.nextLine().trim();
        db.loadFromFile(filename);
    }

    private static void handleCreateIndex() {
        System.out.print("Enter table name: ");
        String tableName = scanner.nextLine().trim();

        System.out.print("Enter field name for index: ");
        String fieldName = scanner.nextLine().trim();

        db.createIndex(tableName, fieldName);
    }
}
