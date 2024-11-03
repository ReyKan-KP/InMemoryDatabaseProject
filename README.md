# In-Memory Database CLI

This is a simple in-memory database implemented in Java, providing basic CRUD operations, transaction support, and other features like indexing and file-based saving/loading. The database operates entirely in memory, making it lightweight and fast, suitable for testing, experimentation, or as a base for a more complex database system.

## Features

- **CRUD Operations**: Insert, retrieve, update, and delete records.
- **Transaction Management**: Start, commit, and roll back transactions.
- **Indexing**: Create indexes for specific fields for faster lookups.
- **Persistence**: Save and load database state to and from files.
- **CLI Interface**: Interact with the database via a command-line interface.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or above
- Recommended: Apache Maven (for building the project)

### Project Structure

Here’s a brief overview of the key files in this project:

- **Main.java**: Entry point of the CLI application.
- **Database.java**: Manages database tables, records, and transaction operations.
- **Table.java**: Represents a table, with methods for managing records and indexing.
- **DbRecord.java**: Represents a record, containing field names and values.
- **CommandProcessor.java**: Handles parsing and execution of CLI commands.
- **CLI Interface**: Allows users to perform operations on the in-memory database.

### Building the Project

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/InMemoryDatabaseProject.git
   cd InMemoryDatabaseProject
   ```

2. **Compile the code**:
   ```bash
   javac -cp lib/* -d out src/database/*.java src/*.java
   ```

   The compiled files will be available in the `out` directory.

### Running the CLI Interface

1. **Start the CLI**:
   ```bash
   java -cp "out;lib/*" Main
   ```

   You will see the following welcome message:
   ```
   Welcome to the In-Memory Database CLI!

   Commands:
   1: Insert
   2: Get
   3: Update
   4: Delete
   5: Save
   6: Load
   7: Create Index
   8: Begin Transaction
   9: Commit
   10: Rollback
   11: Exit
   ```

2. **Using the CLI Commands**

   Here’s a brief guide to each command:

   - **Insert a Record**
     ```plaintext
     Enter command number: 1
     Enter table name: myTable
     Enter record ID: 1
     Enter number of fields: 2
     Enter field name: name
     Enter field value: Alice
     Enter field name: age
     Enter field value: 30
     ```

     This creates a new record in the `myTable` table with the specified fields.

   - **Retrieve a Record**
     ```plaintext
     Enter command number: 2
     Enter table name: myTable
     Enter record ID: 1
     ```
     Output:
     ```plaintext
     {name=Alice, age=30}
     ```

   - **Update a Record**
     ```plaintext
     Enter command number: 3
     Enter table name: myTable
     Enter record ID: 1
     Enter number of fields to update: 1
     Enter field name: age
     Enter new field value: 31
     ```

   - **Delete a Record**
     ```plaintext
     Enter command number: 4
     Enter table name: myTable
     Enter record ID: 1
     ```

   - **Save Database to a File**
     ```plaintext
     Enter command number: 5
     Enter filename: database_backup.json
     ```

   - **Load Database from a File**
     ```plaintext
     Enter command number: 6
     Enter filename: database_backup.json
     ```

   - **Create an Index**
     ```plaintext
     Enter command number: 7
     Enter table name: myTable
     Enter field name: name
     ```

   - **Transaction Management**
     - **Begin Transaction**:
       ```plaintext
       Enter command number: 8
       ```

     - **Insert or Modify Records During Transaction**:
       ```plaintext
       Enter command number: 1
       Enter table name: myTable
       Enter record ID: 2
       Enter number of fields: 2
       Enter field name: city
       Enter field value: New York
       Enter field name: age
       Enter field value: 25
       ```

     - **Commit the Transaction**:
       ```plaintext
       Enter command number: 9
       ```

     - **Rollback the Transaction** (discard any changes made during the transaction):
       ```plaintext
       Enter command number: 10
       ```

3. **Exit the CLI**
   ```plaintext
   Enter command number: 11
   ```

### File Descriptions

- **`src/Main.java`**: Launches the application, initializes the `Database` and starts the CLI.
- **`src/Database.java`**: Core database management, including transaction handling, record operations, and persistence.
- **`src/Table.java`**: Manages a collection of records in a table format, with support for field-based indexing.
- **`src/DbRecord.java`**: Represents individual records stored in a table.
- **`src/CommandProcessor.java`**: Parses and executes commands entered by the user.

### Example Usage

Here’s a sample session illustrating basic functionality:

```plaintext
Commands:
1: Insert
2: Get
3: Update
4: Delete
5: Save
6: Load
7: Create Index
8: Begin Transaction
9: Commit
10: Rollback
11: Exit
Enter command number: 1
Enter table name: myTable
Enter record ID: 1
Enter number of fields: 2
Enter field name: name
Enter field value: Alice
Enter field name: age
Enter field value: 30
Record inserted successfully.

Enter command number: 2
Enter table name: myTable
Enter record ID: 1
{name=Alice, age=30}

Enter command number: 8
Transaction started.

Enter command number: 3
Enter table name: myTable
Enter record ID: 1
Enter number of fields to update: 1
Enter field name: age
Enter new field value: 31

Enter command number: 2
Enter table name: myTable
Enter record ID: 1
{name=Alice, age=31}

Enter command number: 10
Transaction rolled back.

Enter command number: 2
Enter table name: myTable
Enter record ID: 1
{name=Alice, age=30}
```

This session demonstrates how transactions can be used to temporarily modify records, with rollback restoring the original state.

### Contributing

Contributions are welcome! If you’d like to contribute, please fork the repository and create a pull request with your changes.

### License

This project is licensed under the MIT License.

