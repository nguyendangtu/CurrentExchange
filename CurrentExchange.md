Programming Assignment:
-----------------------
Create a Java application (preferably built using Spring) serving
the following use cases.
------------
Data source:
------------
The data contains the currency Exchange rates for a set of
currencies on different dates.
- The data for one day will be stored in one file. i.e There will be
one file for one day.
- The file name format is yyyy-mm-dd.txt (eg: 2017-01-01.txt).
- These files store the exchange rate of following currencies (wrt USD):
1. United States Dollar: USD
2. Swiss Franc: CHF
3. Euro: EUR
4. British Pound: GBP
5. Omani Rial: OMR
6. Bahraini Dinar: BHD
7. Kuwaiti Dinar: KWD
8. Singapore Dollar: SGD
The sample data for 2017-01-01 is shown as below:
file name: 2017-01-01.txt
1 CHF traded at 1.04 times USD
1 EUR traded at 1.19 times USD
1 GBP traded at 1.30 times USD
1 OMR traded at 2.60 times USD
1 BHD traded at 2.65 times USD
1 KWD traded at 3.32 times USD
1 SGD traded at 0.74 times USD
NOTE: You can mock the data for other days for development or
testing purpose.
-----------------
Problem statement:
-----------------
Implement the following business cases and expose them as APIs:
1) Given a date, get the exchange rate of all the currencies (wrt
USD)
2) Given a date and 2 currencies, find the exchange rate between
them
3) Given a date range and a given currency, find the exchange rate
of that currency (wrt USD) for the entire date range
--------------------
Evaluation criteria:
--------------------
1) Completeness and Code Quality
2) Code coverage (Write test cases using Junit)
3) Performance (Think of case where you have years of data, can you
do things in parallel ?)
4) Caching ability. (The first execution should cache the results so
that it can be used in the next execution)
------------
Submission:
------------
- Submit your code either using :
- a public repo on Git or
- a zip folder containing the source code,
test code and dependencies reqd to run the application.
- Send the detailed instructions how to run the application