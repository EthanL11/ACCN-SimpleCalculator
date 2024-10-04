import java.io.*;
import java.net.*;
import java.util.*;
 
public class Server
{
	private Socket socket = null;

	// create port
	public Server(int port)
	{
		try
		{
			// create socket
			ServerSocket serversocket = new ServerSocket(port);
			Socket socket = serversocket.accept();
 
			// get input and output from client
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
 
			while(true)
			{
				// get input
				String input = dis.readUTF();

				if (input.equals("0/0="))
				{
					System.out.printf("Received question \"%s\"; end the server program\n",input);

					// close socket
					socket.close();
					break;
				}
				
				double result=0;

				// remove =
				input = input.replace("=", "");

				// split string
				String[] parts = input.split("([*/+-])");

				// get operand
				String op = input.replace(parts[0], "").replace(parts[1], "");

				// get numbers
				double num1 = Double.parseDouble(parts[0]);
				double num2 = Double.parseDouble(parts[1]);

				// math
				if(op.equals("+"))
				{
					result = num1 + num2;
				}
				else if(op.equals("-"))
				{
					result = num1 - num2;
				}
				else if(op.equals("/"))
				{
					result = num1 / num2;
				}
				else if(op.equals("*"))
				{
					result = num1 * num2;
				}

				// server side
				System.out.printf("Received question \"%s\"; send back answer %.3f\n",input,result);
				
				// send result to client
				dos.writeUTF(Double.toString(result));
			}
		}

		// fail to connect to client
		catch(Exception e)
		{
			System.out.println("Error");
		}
	}
	
	public static void main(String args[])
	{
		Server server = new Server(12342);
	}
}