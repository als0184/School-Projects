<a name="br1"></a> 

**COMP 5120/6120 Database Systems I**

**Term Project: Populating and Querying Databases with SQL**

**Spring 2023**

**Due: 11:50pm, May. 4, 2023**

**1. Project Description**

In this project, you are to create a database for a hypothetical online bookstore system.

The system maintains information about books, subjects, supplier, and shippers. The

bookstore acquires the desired books from suppliers (e.g. Amazon.com), and ships the

books by shippers (e.g. UPS). The system also keeps track of orders, customers, and

employees.

Your tasks are the following:

1\. Create and populate the bookstore database with MySQL.

2\. Create a user interface using PHP and HTML to query and modify the data.

3\. Write correct SQL statements for the queries given.

**2. Access to PHP and MySQL**

All Auburn students have a personal space on the university web server (Mallard), which

has PHP support. You will need to upload your project PHP files to the web server for the

grader to access them. For more information about accessing the web space, visit OIT

website:

[https://auburn.service-](https://auburn.service-now.com/it?id=kb_article_view&sysparm_article=KB0012093&sys_kb_id=56660afe1baa5d50cb564005bd4bcb78&spa=1)

[now.com/it?id=kb_article_view&sysparm_article=KB0012093&sys_kb_id=56660afe1ba](https://auburn.service-now.com/it?id=kb_article_view&sysparm_article=KB0012093&sys_kb_id=56660afe1baa5d50cb564005bd4bcb78&spa=1)

[a5d50cb564005bd4bcb78&spa=1](https://auburn.service-now.com/it?id=kb_article_view&sysparm_article=KB0012093&sys_kb_id=56660afe1baa5d50cb564005bd4bcb78&spa=1)

Students may request to have a database created on MySQL server maintained by OIT.

Details are found a[t](https://cws.auburn.edu/oit/database/mySQL/Create)[ ](https://cws.auburn.edu/oit/database/mySQL/Create)<https://cws.auburn.edu/oit/database/mySQL/Create>[ ](https://cws.auburn.edu/oit/database/mySQL/Create).

Note that the request takes at least two days for the OIT to approve. Request this as soon

as possible when you are starting this project. If there is a problem with the requests, e-

mail the OIT Office Mark Bransby: <bransby@auburn.edu>[.](mailto:bransby@auburn.edu)

AU Global Protect VPN is required for MySQL Workbench and Win SCP to connect to

the web space and database.

For AU Global Protect VPN, visit[:](https://libguides.auburn.edu/vpn)[ ](https://libguides.auburn.edu/vpn)<https://libguides.auburn.edu/vpn>[ ](https://libguides.auburn.edu/vpn).

**3. Populating the Database (20 points)**

Create the following tables for the bookstore database and populate the database from the

**data.zip.** You may optionally first create your user interface and populate the database

through your interface.

1 of 6



<a name="br2"></a> 

**4. Interface Implementation (30 points)**

Implement an interface by using PHP and HTML. A simple example is as shown in the

following figure, which includes a text box to accept a SQL statement and then submit it

to the MySQL database.

**Interface Requirements:**

1\. Your interface should **not** accept SQL DROP statements.

2\. For any other SQL statements, your interface should not only accept it, but also

return the execution result. For example, a select statement will return the query

results (including the attribute name for each column) and **the number of rows**

**retrieved**. A create/delete/update/insert statement will display "Table

Created/Updated", "Row Inserted" or "Row(s) Deleted" messages on your

interface.

3\. An error message should be displayed if an incorrect SQL statement was

submitted.

4\. You should also have a title for this interface, indicating your name.

**5. Execution of SQL queries (50 points)**

First, you need to write the SQL statements for the following queries. Then, you submit

each of them through your interface to get the correct result.

1\. Show the subject names of books supplied by \*supplier2\*.

2\. Show the name and price of the most expensive book supplied by

\*supplier3\*.

3\. Show the unique names of all books ordered by \*lastname1

firstname1\*.

4\. Show the title of books which have more than 10 units in stock.

5\. Show the total price \*lastname1 firstname1\* has paid for the books.

2 of 6



<a name="br3"></a> 

6\. Show the names of the customers who have paid less than $80 in

totals.

7\. Show the name of books supplied by \*supplier2\*.

8\. Show the total price each customer paid and their names. List the

result in descending price.

9\. Show the names of all the books shipped on 08/04/2016 and their

shippers' names.

10\. Show the unique names of all the books \*lastname1 firstname1\* and

\*lastname4 firstname4\* \*both\* ordered.

11\. Show the names of all the books \*lastname6 firstname6\* was

responsible for.

12\. Show the names of all the ordered books and their total

quantities. List the result in ascending quantity.

13\. Show the names of the customers who ordered at least 2 books.

14\. Show the name of the customers who have ordered at least a book in

\*category3\* or \*category4\* and the book names.

15\. Show the name of the customer who has ordered at least one book

written by \*author1\*.

16\. Show the name and total sale (price of orders) of each employee.

17\. Show the book names and their respective quantities for open

orders (the orders which have not been shipped) at midnight

08/04/2016.

18\. Show the names of customers who have ordered more than 1 book and

the corresponding quantities. List the result in the descending

quantity.

19\. Show the names of customers who have ordered more than 3 books and

their respective telephone numbers.

**6. Materials to Hand In**

Please submit the following in a zip file named **YourAuburnUsername.zip** to canvas

with the subject **COMP5120/6120 Term Project** by deadline.

1\. URL of your PHP/HTML interface in a file named **url.txt**. Please make sure the

interface can be used to query the database. The TA will use the interface to

verify that the database has been populated with correct schema and data.

3 of 6



<a name="br4"></a> 

2\. Your code organized in **src/** folder for implementing the interface. This folder

includes the index.php file and other files of your website.

3\. The execution of SQL queries in **sql.txt**. Please have queries numbered and in the

same order as queries given.

7\. Tips: Login to MySQL server with MySQL Workbench8.0

1\. Connect to AU Global Protect VPN

2\.

8\. Tips: Login to Web server with Win SCP

1\. Connect to AU Global Protect VPN

4 of 6



<a name="br5"></a> 

2\.

3\.

4\.

5 of 6



<a name="br6"></a> 

8\. Additional source to learn PHP

<https://www.w3schools.com/php/>

6 of 6

