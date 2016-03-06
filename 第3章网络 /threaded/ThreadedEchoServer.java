package threaded;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by supreme on 16/3/5.
 * 本程序实现一个多线程的服务器端监听端口8189并显示所有客户端的输入
 */
public class ThreadedEchoServer {
    public static void main(String[] args)
    {
        try
        {
            int i = 1;
            ServerSocket s = new ServerSocket(8189);
            while (true)
            {
                Socket incoming = s.accept();
                System.out.println("Spawning" + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                Thread t = new Thread(r);
                t.start();
                i++;

            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}

/**
 * 这个类处理一个客户端输入以连接一个服务器端套接字连接
 */

class ThreadedEchoHandler implements Runnable
{
    private  Socket incoming;
    /**
     * 构造一个处理程序
     */
    public ThreadedEchoHandler(Socket i)
    {
        incoming = i;
    }
    public void run()
    {
        try
        {
            try{
                InputStream inStream = incoming.getInputStream();
                OutputStream outStream = incoming.getOutputStream();

                Scanner in = new Scanner(inStream);
                PrintWriter out = new PrintWriter(outStream,true);

                out.println("Hello! Enter Bye to exit");
                //显示客户端输入
                boolean done = false;
                while (!done && in.hasNextLine())
                {
                    String line = in.nextLine();
                    out.println("Echo: " + line);
                    if (line.trim().equals("Bye"))
                        done = true;
                }
            }
            finally {
                incoming.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}


