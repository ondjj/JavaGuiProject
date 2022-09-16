package ch16;

import java.net.URL;

public class NetworkEx2 {

	public static void main(String[] args) throws Exception{

		URL url = new URL("http://www.codechobo.com:80/sample/" + "hello.html?referer=codechobo#index1");
		
		System.out.println("url.getAuthority(): " + url.getAuthority());
//		System.out.println("url.getContent(): " + url.getContent());
		System.out.println("url.DefaultPort(): " + url.getDefaultPort());
		System.out.println("url.Port(): " + url.getPort());
		System.out.println("url.GetFile(): " + url.getFile());
		System.out.println("url.GetHost(): " + url.getHost());
		System.out.println("url.GetPath(): " + url.getPath());
		System.out.println("url.getProtocol(): " + url.getProtocol());
		System.out.println("url.getQuery(): " + url.getQuery());
		System.out.println("url.getRef(): " + url.getRef());
		System.out.println("url.getUserInfo(): " + url.getUserInfo());
		System.out.println("url.toExternalForm(): " + url.toExternalForm());
		System.out.println("url.toURI(): " + url.toURI());
		
		
		
	}

}
