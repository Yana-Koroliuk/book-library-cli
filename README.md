# book-library-cli

## Relevance

The library management program can be used in various contexts where there is a need to organize and manage a library's
collection of books. Here are some scenarios where the program can be useful: public libraries, educational
institutions, personal libraries. Overall, the program offers an efficient and user-friendly solution for managing
library resources, enhancing accessibility, and simplifying administrative tasks. It can be used in a wide range of
library settings to improve organization, user experience, and overall library management.

## Concept

The library management program is a versatile system designed to manage various aspects of a library. It provides
functionality for creating, updating, and deleting books, authors, categories, and orders. Users can perform actions
such as searching for books, placing orders, returning books, and checking their order history.

The program enables administrators to efficiently manage the library's inventory by adding new books,
updating book details, and deleting books when necessary. Authors and categories can be created and associated with
books to organize them effectively. Users can search for books based on criteria such as title, author, and category,
allowing them to find specific books of interest.

The order management feature allows users to place orders for books within a specified timeframe. They can view their
order history, including both returned and unreturned books. Returning a book updates the order status and ensures
accurate tracking of borrowed books.

The program also includes user authentication and authorization to ensure secure access to the library system. Users are
required to log in before performing any actions, providing an added layer of security.

## Program

The program works as an interactive form. The following commands can be used to work with the program:

* **search "Book Name" [Author S., Author T.] "Category 1"** - searches for books based on the provided criteria.
  Parameters
  are optional, so you can perform a search with any combination of criteria.
* **order-book bookId [2023-05-01, 2023-05-02]** - places an order for a book with the specified ID, specifying the
  start
  and end dates of the order.
* **return-book orderId** - marks a book order as returned based on the provided order ID.
* **status-book bookId** - Retrieves the status of a book, including the number of available exemplars.
* **status-user** - retrieves the status of the current user, such as the list of books they have borrowed and returned.
* **create-book "Book Name" [Author S., Author T.] "Category 1" countOfExemplars** - creates a new book with the given
  name, authors, category, and number of exemplars.
* **create-author "Name Author"** - creates a new author with the given name.
* **create-category "Name category"** - creates a new category with the given name.
* **update-book bookId "NewName Book" [Author S., Author T.] "Category 1" countOfExemplars** - updates the information
  of an existing book with the specified ID, including the name, authors, category, and number of exemplars.
* **update-author authorId "Author New"** - updates the name of an existing author with the specified ID.
* **update-category categoryId "Category New"** - updates the name of an existing category with the specified ID.
* **update-user userId "UserName New" password** - updates the information of an existing user with the specified ID,
  including the username and password.
* **delete-book bookId** - deletes the book with the specified ID.
* **delete-author authorId** - deletes the author with the specified ID.
* **delete-category categoryId** - deletes the category with the specified ID.
* **delete-user userId** - Deletes the user with the specified ID.
* **delete-order orderId** - Deletes the order with the specified ID.
* **login** - allows the user to log in to the system.
* **register** - allows a new user to register in the system.
* **logout** - logs out the currently logged-in user.

Without being authorized, you can only search for a book by various criteria and check the status of the book. You need
to log in to use all other commands.
All code has been verified by SonarLint. All dependencies are listed in **pom.xml** flie.

## Technological stack

* Java
* JUnit
* Mockito - Mocking Framework
* PostgreSQL - Database System
* SonarQube as linter and static code validator
* Maven - Dependency Management

## Help

Ask questions at https://t.me/Koroliuk_Yana and post issues on GitHub.

## LicenseGNU

Don't forget about licence. This program is GNU General Public licensed.