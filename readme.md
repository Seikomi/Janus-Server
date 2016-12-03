# ![Janus Server](resources/janus-icon.png) (dev version)

## Presentation

Welcome on the project Janus, a sockets based server running on Java Runtime Environment (above Java 8).

This server use two TCP ports like a FTP server, one for command and the other for data. All it configuration
is made in a unique `.properties` file.
The command port is used to send commands to the server. All commands is handle by a *command pattern* implementation, 
which allow to add new commands. And the data port is used to send file between the client and the server.

An high focus on qualities over quantities is apply in this project, i run sonar analysis (or other qualities tool) on 
the project before every major release and correct all issues and get a test coverage over 75%. 

The main goal of this project is to easily be extended in a more complex services server with a design oriented on user
defined commands and inheritance.
You can see a example of an extension with the project GrooveBerry.

## Contact

[nicolas.symphorien@gmail.com](mailto:nicolas.symphorien@gmail.com)