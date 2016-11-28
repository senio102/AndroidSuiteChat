# AndroidSuiteChat

This applicaiton currently uses a java TCP client running on an android device to connect to an outside server.
As of this release, the application currently has the ability to take an IP address and port number from the user and send a connection request.
The user can then chat from the phone to the server and the server can write back.
The client uses threads for the ability to send and recieve messages simultaneously.
The server also has the ability to handle more than one client at onces using multiple threads.

The application currently has too many files to upload them to Githib, although i have posted screenshots and the main java files i worked on.
The APK file is also available to download and run via a desktop application such as BlueStacks.

To run the application, open it via an emulator and run the server in a terminal window.

If doing both on the same machine, when prompted for the IP address at the application screen, enter 10.0.2.2

The port is set to 6789 by deault on the server side, but it can be changed.

Messages sent from the client and received by the server are currently echoed back to the client in all caps for testing.
