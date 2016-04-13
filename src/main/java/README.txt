README

1. Parser class for move and all messages. 
	- constructor
	- parse move string method
	- send to error check
	

2. Valid move class / server asks this class for all of moves / central move database
3. TESTS
4. Broadcast


To Game, Can only be run with 2 players succesfully right now.

Open 3 terminals. 

2 server teminals 
1 client terminal

In the server teminals enter:


java -cp build/libs/Quoridor.jar GameServer --port <Port1>
java -cp build/libs/Quoridor.jar GameServer --port <Port2>

once you spin them up, you give them both a name and hit enter.

then you can spin up the client

java -cp build/libs/Quoridor.jar GameClient localhost:<port1> localhost:<port2>

Game will run with 2 AI's they are both the same so it isnt very interesting :)

