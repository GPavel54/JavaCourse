import java.io.*;
import java.net.*;

public class webServer 
{

  public static void main(String[] args) throws IOException 
  {
    System.out.println("Welcome to Server side");
    BufferedReader in = null;
    PrintWriter outt = null;

    ServerSocket servers = null;
    Socket fromclient = null;
    // final String dir = System.getProperty("user.dir");
    servers = new ServerSocket(8787);

    System.out.println("Waiting for a client...");
    fromclient= servers.accept();
    System.out.println("Client connected");

    in  = new BufferedReader(new InputStreamReader(fromclient.getInputStream()));
    outt = new PrintWriter(fromclient.getOutputStream(),true);
    String input;

    System.out.println("Wait for messages");
    while ((input = in.readLine()) != null) 
    {
      String response = new String("HTTP/1.0 200 OK\nServer: Myserver\nContent-Language: ru\nContent-Type: text/html; charset=utf-8\nContent-Length: ");
      if (input.contains("GET "))
      {
        String tmp;
        int position;
        position = input.indexOf("GET");
        String parsed = input.substring(position + 4);
        parsed = parsed.substring(0, parsed.indexOf("HTTP") - 1);
        if (parsed.equals("/"))
        {
          int c;
          FileInputStream fin = new FileInputStream("../resources/index.html");
          String page = new String();
          while ((c = fin.read()) != -1)
          {
            page += (char) c;
          }
          System.out.println("Page:" + page);
          fin.close();
          response += page.length() + "\nConnection: close\n\n" + page;
        }
        outt.println(response);
      }
    }
    outt.close();
    in.close();
    fromclient.close();
    servers.close();
  }
}
