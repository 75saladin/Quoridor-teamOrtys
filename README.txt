Hi, my name is Nick
Hi, my name is Lucas
Hi, my name is Eric
Hi, my name is Jed
Hi, my name is Craig

Quick and dirty pseudocode for the Server wrapper:

constructors:
ServerNetworkManager()
ServerNetworkManager(int port)

Methods: 
boolean sendPawnMove(int row, int col) - Takes the row and column (on a 9x9 grid) of where you want the pawn to go. Returns whether or not it was successful.

boolean sendWallMove(int row, int col, boolean isHorizontal) - See the documentation from Ladd for the position of the wall.

boolean sendStringLiteral(String toSend) - Exactly what it says on the tin

boolean moveRequested() - returns true if a move is requested of this server, false otherwise.

int hasPlayerWon() - returns the winning player number, or 0 if no one has won

int playersKicked() - returns a number of players kicked (e.g. if players 1 and 3 have been kicked, will return 13, or if 2, 3, and 4 have been kicked, will return 234. The position of the digits occurs in order of kick)

boolean hasAMove() - returns true if the server has recieved a new move. False otherwise

String getNextMove() - returns the most recent move that has been recieved.

void close() - Closes this connection
