package ATMServer_Group10;

import java.io.*; 
import java.net.*; 
import ATMServer_Group10.DbManager;
import ATMServer_Group10.RFC;

public class Server {
	
	String userid, passwd, sendToClient;
    Double amount, wdamount;
    RFC useRFC = new RFC();
	
	
	
	public static void main(String argv[]) throws Exception{
		ServerSocket welcomeSocket = new ServerSocket(2525); 
	    while(true) {
	    	String getFromClient;
	    	Server server = new Server();
	    	Socket connectionSocket = welcomeSocket.accept();
	    	BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
	    	getFromClient = inFromClient.readLine();
        	System.out.println("Get from client: " + getFromClient);
//	    	if(getFromClient.substring(0, 4).equals("HELO")) {
//	    		server.checkUserid(connectionSocket);
//	    	}
	    	
	    	if(getFromClient.substring(0, 4).equals("HELO")) {
	    		//AUTH(check userid)
	    		server.userid = getFromClient.substring(5, (getFromClient.length()));
	        	PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true); 
	        	server.sendToClient = server.useRFC.askForPw(server.userid);
	        	outToClient.println(server.sendToClient);
	        	System.out.println("Send to client: " + server.sendToClient);

	    		getFromClient = inFromClient.readLine();
	        	System.out.println("Get from client: " + getFromClient);
	    		if(getFromClient.substring(0, 4).equals("PASS")) {
	    			//checkPassword
	    			inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream())); 
	    	    	server.passwd = getFromClient.substring(5, (getFromClient.length()));
	    	    	outToClient = new PrintWriter(connectionSocket.getOutputStream(), true); 
	    	    	server.sendToClient = server.useRFC.check(server.userid, server.passwd);
	    	    	outToClient.println(server.sendToClient);
	    	    	System.out.println(server.sendToClient);
	    		}
	    	}
	    	
        	for( ; ; ) {
        		for( ; ; ) {
        			getFromClient = inFromClient.readLine();
        			if(getFromClient!=null) {
        				System.out.println("Get from client: " + getFromClient);
        				break;
        			}
        		}
    	    		if(getFromClient.equals("BALA")) {
    	    			//sendAmount
    	    			PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true); 
    	    	    	server.sendToClient = server.useRFC.sendAmount();
    	    	    	outToClient.println(server.sendToClient);
    	    	    	System.out.println(server.sendToClient);
    	    			server.useRFC.Journal(server.userid, "查询余额");
    	    		}else if(getFromClient.substring(0, 3).equals("BYE")) {
    	    			PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true); 
    	    	    	outToClient.println("BYE");
    	    	    	System.out.println("BYE 已和客户端断开连接");
    	    	    	break;
    	    		}else if(getFromClient.substring(0, 4).equals("WDRA")){
    	    			//withdraw
    	    			server.wdamount = Double.valueOf(getFromClient.substring(5, (getFromClient.length())));   //20240522新增
    	    			System.out.println("取款金额为："+server.wdamount);
    	    			PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true); 
    	    	    	server.sendToClient = server.useRFC.withdraw(server.wdamount);
    	    	    	server.useRFC.Journal(server.userid, "取款" + server.wdamount + "元");
    	    	    	outToClient.println(server.sendToClient);
    	    	    	System.out.println(server.sendToClient);
    	    		}else {
    	    			PrintWriter outToClient = new PrintWriter(connectionSocket.getOutputStream(), true); 
    	    	    	server.sendToClient = server.useRFC.getERROR();
    	    	    	server.useRFC.Journal(server.userid, "出错");
    	    	    	outToClient.println(server.sendToClient);
    	    	    	System.out.println(server.sendToClient);
    	    		}
        	}		    		
        } 
    } 
}
