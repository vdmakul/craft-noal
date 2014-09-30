Loan
====
**Create a simple, production-ready micro-lending application API and deploy it to whatever cloud**

**Business requirements:**

 * User can apply for loan by passing amount and term to api.
 * Loan application risk analysis is performed. Risk is considered too high if:
    * the attempt to take loan is made after 00:00 with max possible amount.
    * reached max applications (e.g. 3) per day from a single IP.
 * Loan is issued if there are no risks associated with the application. If so, client gets response status ok. However, if risk is surrounding the application, client error with message.
 * Client should be able to extend a loan. Loan term gets extended for one week, interest gets increased by a factor of 1.5.
 * The whole history of loans is visible for clients, including loan extensions.

**Technical requirements:**

* Backend in Java 7+, XML-less Spring, Hibernate (JPA is OK).
* Code is production-ready, covered with unit tests.
* Acceptance tests for the happy path scenario included.
* Ability to run application from the command line.

**What gets evaluated:**

 * Conformance to requirements
 * Code quality (both production and test)
 * How simple it is to run the application (embedded DB/embedded container)
