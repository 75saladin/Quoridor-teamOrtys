

public Parser {

    String input;

    public Parser (String s) {
        input = stripBrackets(s);
        String[] arr = input.split(" ");
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
               handleMyoushu();
            case "TESUJI":
               handleTesuji();
            case "ATARI":
               handleAtari();
            case "GOTE":
               handleGote();
        }
    }

    public String stripBrackets(String s) {
        s=s.replaceAll("[\\[()\\],]+", "");
	String[] temp = s.split(" ");
	return s;
    }

    public void handleHello() {
        // Send "IAM" to server
    }

    public void handleIam()


}
        
