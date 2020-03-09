# Backend
Business Logic and API for the Auto-Garcon application

# Building
`mvn install`  
`mvn package`  
`sudo mvn --define DB_PASS="[your password]" --define DB_USER="[your username]" -Dexec.mainClass="AutoGarcon.Main" exec:java` 
  
run with su because we bind to port 80. 



## Contributing
Submit a pull request! See how [here](https://zachmsorenson.github.io/tutorials/github). 

# Licensing
We follow the GPL 3.0 license.
