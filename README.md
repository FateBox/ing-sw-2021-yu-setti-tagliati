# Software Engineering Final Project 2020/2021

## Introduction
**Masters of Renaissance** is a strategy board game for 1-4 players.
It offers deep strategic choices of action selection and engine building.

Rulebooks and project requirements: [Read here](/Deliverables/Rules/)

## Implementation
| Features | State |
|:-----------------------|:------------------------------------:|
| Complete Rules | ok |
| Socket | ok |
| CLI | ok |
| GUI | ok |
| Multiple matches | ok |

* FA: Multiple matches   
  The server can host multiple matches in the same time.

## Run application
Application jar files: [Click here](/Deliverables/Jars/)  
Java 15 is required.

### Server
You can run the server application by the following command:
```sh
java -jar Server.jar
```

### Client
You can double-click on Client.jar or use the following command in the terminal:
```sh
java -jar Client.jar [param]
```
It launches the GUI if there are no parameters, otherwise it runs the CLI.



## Test Coverage
<p align="center">
<img src="/Deliverables/TestReport/Test.png">
</p>

## Team
**GC42**: Federico Setti([@FedericoSettiPolimi](https://github.com/FedericoSettiPolimi)), Jie Yu([@FateBox](https://github.com/FateBox))

