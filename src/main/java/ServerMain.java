public class ServerMain{
    
    private static ServerWrapper server;

    public static void main(String[] args){
	int port = ServerNetwork.DEFAULT_PORT;
	if(args.length > 0){
	    if(args[0].equals("--port") && args[1].matches("\\d+")){
		port = Integer.parseInt(args[1]);
		if(port < 1024 || port > 65535){
		    port = ServerNetwork.DEFAULT_PORT;
		    System.out.print("Invalid port number, defaulting to:");
		    System.out.println(" " + ServerNetwork.DEFAULT_PORT);
		}else{
		    System.out.println("Using port: " + port);
		}
	    }else{
		System.out.print("Invalid arguments, defaulting to port:");
		System.out.println(" " + ServerNetwork.DEFAULT_PORT);
	    }
	}else{
	    System.out.print("Defaulting to port:");
	    System.out.println(" " + ServerNetwork.DEFAULT_PORT);
	}
	server = new ServerWrapper(port);
    }

    public static ServerWrapper getNetwork(){
	return server;
    }

}