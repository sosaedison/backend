# Backend
Business Logic and API for the Auto-Garcon application. To reach us with any concerns, email us at managerwebautogarcon@gmail.com .

# Building
Make sure you have the java build system maven installed. 
As well as a java runtime and compiler. 

The project's dependecies are added to pom.  
On debian like linux distros you can install maven with:  
`sudo apt install maven`  

For windows you will need to follow apache's guide as it involves setting up enviroment variables. 
After maven has been installed you can continue on with the build setup. 
  

`mvn install`  
`mvn package`  
`sudo mvn --define DB_PASS="[your password]" --define DB_USER="[your username]" -Dexec.mainClass="AutoGarcon.Main" exec:java` 
  
run with su because we bind to port 80. 



## Contributing
Submit a pull request! See how [here](https://zachmsorenson.github.io/tutorials/github). 

# Licensing
We follow the GPL 3.0 license.
