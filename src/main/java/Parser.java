

public class Parser {

    String input;
    String arr[];

    public Parser (String s) {
        input = stripBrackets(s);
        arr = input.split(" ");
        handle();
    }

    public void handle() {
        String[] arr = input.split(" "); 
        String s = arr[0];
        switch(s) {
            case "HELLO":
               handleHello();
            case "IAM":
               handleIam();
            case "GAME":
               handleGame();
            case "MYOUSHU":
       //        handleMyoushu();
            case "TESUJI":
         //      handleTesuji();
            case "ATARI":
           //    handleAtari();
            case "GOTE":
             //  handleGote();
        }
    }

    public static String stripBrackets(String s) {
        s=s.replaceAll("[\\[()\\],]+", "");
	String[] temp = s.split(" ");
	return s;
    }

    public void handleHello() {
        // Client: Send "IAM" to server
    }

    public void handleIam() {
        // Server: send preferred display name to client. Name cannot contain whitespace.
    }

    public void handleGame() {
    
    }

}
        
