# ZoterobibTestFramework using Selenium with Java, TestNG and Maven
This is a Test Framework for ZoteroBib Application(https://zbib.org/). Added Test scripts for both UI and API automation.

###libraries used
Selenium
TestNG
RestAssured
hamcrest
Allure Report


### Steps to clone and execute the tests
```
git clone https://github.com/hariprasanth-1394/ZoterobibTestFramework
cd ZoterobibTestFramework

UI : mvn clean test -DsuiteXmlFile=UI_Testng.xml
API : mvn clean test -DsuiteXmlFile=API_Testng.xml
```
