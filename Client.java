import java.io.*;
import java.net.*;
import java.util.*;
 
public class Client
{
	private Socket socket = null;
	public Client(String address, int port)
	{
		try
		{
			Scanner scan = new Scanner(System.in);
			
			// connect to socket and port
			socket = new Socket(address, port);
			System.out.printf("Connected with the server on [%s]\n",address);
 
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
 
			while(true)
			{
				// get input from client
				String input = scan.nextLine();

				// checks for kill command
				if(input.equals("0/0="))
				{
					System.out.printf("User input ends; end the client program\n");
				}
				
				// send input to server
				dos.writeUTF(input);
 
				// get result from server
				String result = dis.readUTF();
				System.out.printf("Answer from the server: %s\n",result);

				// kill client side
				if(input.equals("0/0="))
				{
					break;
				}
			}
		}

		// Fail to connect
		catch(Exception e)
		{
			System.out.println("Fail to Connect");
		}
	}
 
	public static void main(String args[])
	{
		Client client = new Client("127.0.0.1", 12342);
	}
}