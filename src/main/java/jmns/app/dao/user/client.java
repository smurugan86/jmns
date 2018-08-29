package jmns.app.dao.user;

import java.io.*;
import java.net.*;

class client {
	public static void main(String args[]) {
		try {
			for (;;) {
				Socket soc = new Socket("192.168.1.151", 9090);// or ipv4 address
																// for different
																// computers
				BufferedReader is = new BufferedReader(new InputStreamReader(
						System.in));
				PrintStream pr = new PrintStream(soc.getOutputStream());
			
			
				System.out.println("Enter message..");
				String msg = is.readLine();
				pr.println(msg);
				System.out.println("YOU ENTERED.." + msg);
			}
			//soc.close();
		} catch (IOException e) {
			System.out.println(e);

		}
	}
}