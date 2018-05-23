# alternate-mongo-client
Alternate db client solution

Execute gradle wrapper task if wrapper was not properly installed on your system

Edit application.yml with your db connection settings(no authorisation yet - tbd...)

Make sure your local mongo db instance is preliminary started before starting application

Start application with RestApplication.java or ConsoleApplication.java

In case of rest api go to localhost:8080 for testing

Run integration tests via task verification:integrationTest

./build/libs/ - contains bootable jars

