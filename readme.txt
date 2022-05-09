# IJA Project 2022
## Class and sequence diagram editor
### Authors: Vladimir Meciar (xmecia00), Rastislav Budinsky (xbudin05)

### About

This project aims to deliver class and sequence diagrams editor, which will contain some basic check for correct types, method calls in sequence diagrams and so on.

### Start

Project building is done via maven. Maven, takes care of all included libraries and modules. For execution of maven run commands

 * mvn package - compile java files, run tests and create .jar file
 * mvn test - compile java files and run tests
 * mvn clean - remove all compiled java files

 ### Usage

 ![GUI](gui-screenshot.png)

 When opening application, empty window appears, from this part user needs to create new file or load from saved files.
 User then will be able to modify loaded or created class diagram.
 On left side of window user will be able to choose from adding new component of graph and edition of these components.
 To switch to sequence diagram, go to Add component view and on the bottom of page scroll on wished sequence diagram and click switch.
 To repaint relations go to help -> repaint content.
 Undo feature is also implemented and when wishing to execute go to Edit -> Undo.
 When trying to go back to class diagram, go to  Edit -> Open class diagram.
 To check correctness of diagram go to Edit -> Check correctness



 ### Attributes

 Realtions are represented by colour.
 In class diagrams:
    * blue - associaton
    * cyan - aggregation
    * orange - composition
    * black - generalization
    * pink - implements

 In sequence diagrams:
     * blue - synchronous message
     * cyan - asynchronous message
     * orange - return message
     * black - creation of object
     * pink - destruction of object

### Exit
    When exiting simply click the x button in the right upper corner.
    Upon saving file, extension .json needs to be added since loading saved files requires .json files.
