This project is a fully functional banking system developed as the final project for SE 181 at Drexel University. Designed and implemented over a 6-week period, the system emphasizes clean, modular code through Object-Oriented Programming (OOP) principles,
particularly focusing on polymorphism to manage various account types and functionalities efficiently. The project also follows a Test-Driven Development (TDD) methodology, ensuring robust, reliable code that meets predefined requirements.

Key Features:
Account Management: Supports multiple account types, each with its own unique attributes and methods for operations like deposit, withdrawal, and balance inquiry.
Transaction Logging: Tracks each transaction with timestamps for comprehensive records, aiding transparency and accountability.
User Authentication: Includes a basic authentication system to secure account access and user data.
Error Handling: Implements thorough exception handling for an uninterrupted user experience.

Core Concepts and Methodologies:
1. Object-Oriented Programming (OOP) and Polymorphism
Class Hierarchies: Account types are organized in a hierarchy, with a base Account class that contains shared properties and behaviors, while specific account types (like Checking, Savings, etc.) inherit from it. This enables easy maintenance and expansion of account types.
Polymorphism: Through polymorphism, each account type can override base methods, allowing each account to behave according to its specific requirements (e.g., unique withdrawal or interest policies). This modular approach enhances code readability and extensibility.
2. Test-Driven Development (TDD)
Testing Before Implementation: The project was developed using TDD, starting with test cases that define the expected behavior for each component. Each feature was developed incrementally, with tests driving the code design.
JUnit Framework: JUnit is utilized for unit testing, covering individual methods as well as integration testing for complex interactions. This process not only catches errors early but also supports refactoring by ensuring that each component works as expected.
3. Modular Design and Separation of Concerns
Classes and Packages: The project is organized into packages and classes that separate different functionalities, such as account management, transaction logging, and user interaction. This keeps the code organized and simplifies troubleshooting and future expansion.
Single Responsibility Principle: Each class and method has a single, clear purpose, improving readability and maintainability.
4. Exception Handling and Input Validation
Error Handling: The system is designed with comprehensive exception handling, ensuring graceful failure and user-friendly error messages when encountering issues like insufficient funds or invalid inputs.
Validation Mechanisms: Input validation ensures that data entered by users adheres to required formats, enhancing security and reducing potential for user errors.

Credits
Author: Teimurazi Bakuradze

