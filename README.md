# 📁 Spring Boot File Manager Application

A **Spring Boot–based File & Folder Management System** that mimics a real file system with **hierarchical folders and files**, supporting **create, rename, delete, and tree traversal** operations.

This project demonstrates **clean architecture**, **REST APIs**, **tree-based data structures**, and **real-world backend design patterns** commonly discussed in **MAANG interviews**.

---

## 🚀 Features

- Create **root folders**
- Create **nested folders & files**
- Rename files or folders
- Delete a folder/file (with children handling)
- Fetch folder structure as a **tree**
- Expand / collapse UI support
- REST-based backend (UI optional)

---

## 🏗️ Tech Stack

- **Java 21**
- **Spring Boot 4**
- **Spring Web (REST)**
- **MongoDB**
- **Thymeleaf** (UI rendering)
- **JavaScript + CSS** (tree UI)
- **Maven**

---

## 📂 Project Structure

```md
├── repository
│   └── FileNodeRepository.java
│
├── model
│   ├── FileNode.java
│   └── NodeType.java
│
├── dto
│   └── FileNodeDto.java
│
└── FileManagerApplication.java
```

```md
src/main/resources
├── templates
│   └── index.html
│
├── static
│   ├── css
│   │   └── tree.css
│   └── js
│       └── tree.js
│
└── application.yml
```

🧠 Core Logical Design (Interview Critical)

📌 Data Structure Used: **N-ary Tree**

This project models a **file system**, which is naturally a **tree structure**.

Each **folder** can contain:
- multiple folders
- multiple files

Each **file**:
- belongs to exactly one folder
- cannot have children

This forms a **directed, acyclic hierarchy**, identical to how real operating systems model files.

---

📄 FileNode – Core Data Model

Every file or folder in the system is represented by a **single unified entity** called `FileNode`.

```md
FileNode
├── id
├── name
```

### id

- Unique identifier for each node (file or folder)
- Generated automatically by the database
- Used to:
  - Link child nodes to their parent
  - Build the directory tree structure
  - Perform fast lookup and traversal operations

---

### name

- Human-readable name of the file or folder
- Examples:
  - `RootFolder`
  - `Documents`
  - `resume.pdf`
- Can be renamed without affecting tree relationships
- Typically validated to:
  - Not be empty
  - Be unique within the same parent directory

---

### type

- Represents the nature of the node
- Defined using an enum:

```java
FILE
FOLDER
```

**Purpose:**

- `FOLDER` → can contain child nodes
- `FILE` → leaf node, cannot contain children

📌 **Why enum instead of boolean?**

- More expressive and self-documenting  
  (`NodeType.FOLDER` is clearer than `isFolder = true`)
- Prevents ambiguous states
- Easier to extend in the future:
  - `SYMLINK`
  - `ARCHIVE`
  - `SHORTCUT`
- Improves code readability and maintainability
- Reduces logical bugs

> Interview Tip: Enums are preferred over booleans when multiple meaningful states exist.

---

### parentId

- Stores the ID of the parent folder
- `null` → root-level folder
- Enables:
  - Tree construction
  - Recursive traversal
  - Expand / collapse UI behavior

📌 **Rule**
- Only `FOLDER` nodes can be parents
- `FILE` nodes must always be leaf nodes

---

## ⚙️ How the Application Works

### 1️⃣ Create Root Folder

```bash
curl --location 'http://localhost:8080/api/files' \
--header 'Content-Type: application/json' \
--data '{
  "name": "RootFolder",
  "type": "FOLDER",
  "parent": null
}'
```

Creates the root node
✔ parentId = null

2️⃣ Create Child Folder / File

Provide parentId

Backend validates:

Parent exists

Parent is a FOLDER

3️⃣ Fetch Directory Tree

All nodes are fetched

Tree is built using parentId mapping

Returned as nested JSON

Ideal for UI expand/collapse

4️⃣ Rename Node

Update only the name

ID and relationships remain unchanged

5️⃣ Delete Node

If file → deleted directly

If folder → deleted recursively with all children

📌 Uses DFS (Depth First Search) to prevent orphan nodes

🧮 Algorithms & Complexity (MANG Focus)
Operation	Algorithm	Time
Tree build	DFS	O(n)
Search by ID	Hash / DB index	O(1)
Delete folder	Recursive DFS	O(k)

(k = number of descendants)

🌐 REST API Endpoints
Method	Endpoint	Description
POST	/api/files	Create file/folder
GET	/api/files/tree	Fetch directory tree
PUT	/api/files/{id}	Rename file/folder
DELETE	/api/files/{id}	Delete file/folder
🏗️ Project Structure
src/main/java/com/example/filemanager
│
├── controller
│   ├── FileNodeController.java
│   └── HomeController.java
│
├── service
│   ├── FileNodeService.java
│   └── FileNodeServiceImpl.java
│
├── repository
│   └── FileNodeRepository.java
│
├── model
│   ├── FileNode.java
│   └── NodeType.java
│
├── dto
│   └── FileNodeDto.java
│
└── FileManagerApplication.java

🎯 Why Interviewers Love This Project

This project demonstrates:

Real-world system modeling

Tree data structures

Recursive problem solving

Clean API contracts

Separation of concerns

Scalability-ready design

🚀 Same core logic used in:

Google Drive

Dropbox

File Explorers

Content Management Systems

✅ Next Possible Enhancements

File permissions

File size & metadata

User ownership

Drag & drop UI

Pagination for large trees

Author: @MasterHardik
Tech Stack: Spring Boot, Java 21, REST APIs
DSA Focus: Trees, Recursion, DFS
