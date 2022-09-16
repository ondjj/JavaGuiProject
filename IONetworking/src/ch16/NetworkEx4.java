package ch16;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkEx4 {

	public static void main(String[] args)throws Exception {

	URL url = new URL("http://www.codechobo.com/sample/hello.html");
	BufferedReader input = null;
	String line = "";
	
	try {
		
		input = new BufferedReader(new InputStreamReader(url.openStream()));
		
		while((line=input.readLine())!= null) {
			System.out.println(line);
		}
		input.close();
	}catch(Exception e) {
		e.printStackTrace();
	}
	}

}
