# Task Master 
### Brief -
It is a todo app made in spring boot & postgresql database. 
But it have some features (I consider features). 

#
1. It have a index page that shows today's task only. 
2. We can upload task through excel file.

So we can create a excel file containing content & date of that task. And upload it. 
Now Today's task will show only limited task. 

We can prepare such excel file through any ai chat bot, and upload it. 

We should set it as default page for our browsers. 

### File Structure 

* src/main/java/com/pranton/TaskMaster
    * controllers/
        * MainController.java
        * TaskController.java
    * models/
        * Task.java
    * repos/
        * TaskRepo.java
    * services/
        * TaskService.java
    * TaskMasterApplication.java

* src/resources/
    * static/
        * SampleFile.xlsx
    * templates/
        * allTasks.html
        * fileUpload.html
        * index.html
