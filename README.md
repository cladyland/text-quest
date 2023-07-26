# Text quest
## Description
Text-quest is a web application in which the player answers questions in order to win the game.
Each subsequent question depends on the previous one. In case of victory or defeat, the player sees his 
statistics and can restart the game.

![the work of the program](https://i.imgur.com/Znjd4CK.png)
![the work of the program2](https://i.imgur.com/Z6qlHou.png)
### Main packages
* **/java/../../**
  - ```common``` - contains general classes: constants, dto, entities, exceptions, mappers
  - ```db``` - repositories for storing and managing information
  - ```filter``` - classes for pre-checking and filtering requests
  - ```service``` - connecting chain between repositories and servlets
  - ```servlet``` - connecting chain between UI and business logic
  - ```util``` - application utils
  - ```AppContextListener``` - the application entry point
* **resources** - application data and log4j configuration
* **webapp** - UI files (css, jsp, images, web.xml)
## Launch
1. download and extract [clady_quest.zip](https://drive.google.com/file/d/1DZPT5nKUwSwvZ1-2bgrLXf1rfEssonF_/view?usp=sharing)
2. run Docker
3. open the command line end enter: 
   * ```$ docker load -i clady_quest.tar```
   * ```$ docker run -p 7777:8080 --name clady_quest text-quest:2.0```
4. after launch open the browser and go to the link: ```localhost:7777```
## Technologies
* Java 16, Maven, Docker
* Servlets, log4j, mapstruct, junit, mockito, lombok
* HTML, CSS, jstl, Bootstrap
***
#### Notice from the first readme that I published for deadline to remember how it was
> _My mentors often remind us that everything can be Googled. I agree with that. But there is one important 
thing that I understood only in new conditions... for this there must be light and the Internet :)_     
_This project was really challenging and very interesting. Despite everything, it's a great experience. 
Sorry guys for the uninformative readme file... I was really trying to write good code._
