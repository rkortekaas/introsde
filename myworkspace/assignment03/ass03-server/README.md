INTROSDE 2016 Assignment 03: SOAP Web Services
===============

--------

The code
-------------

In the /src folder there are the Java classes; the package dao ("data access object", a typical data accessing pattern) contains LifeCoachDao the enum class that manages the connection to the database.
The 'model' package contains the Person and the Measure java classes that map to the database tables; the 'ws' package contains an interface _People_ and its implementation _PeopleImpl_ , the first class describes all web methods exposed by my web service and the second implements the methods. Finally, the 'endpoint' package contains the _PeoplePublisher_ class that is responsible to create the web service.

#### This service was deployed on heroku and is in this link:
https://introsde2016-assignment3.herokuapp.com


#### The client to execute requests can be found here:
https://github.com/michelebof/introsde-2016-assignment-3-client




----------

How run the code 
---------------------
The code can be run simply execute in the terminal ```git clone https://github.com/michelebof/introsde-2016-assignment-3```
Then:
 - ```ant start``` : to install all the dependencies and to run the standalone server




