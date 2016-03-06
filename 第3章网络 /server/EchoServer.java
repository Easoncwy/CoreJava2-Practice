package server;

import javafx.print.Printer;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by supreme on 16/3/5.
 */
public class EchoServer {
    public static void main(String[] args) throws Exception
    {
        //建立server socket
        try(ServerSocket s = new ServerSocket(8189))
        {
            //等待客户端连接
            try (Socket incoming = s.accept())
            {
                InputStream inStream = incoming.getInputStream();
                //服务器发送到服务器输出流的所有信息都会成为客户端程序的输入,
                OutputStream outStream = incoming.getOutputStream();
                //来自客户端程序的所有输出都会被包含在服务器输入流中.
                try(Scanner in = new Scanner(inStream))
                {
                    //因为要通过套接字来发送文本
                    //所以将流转换成扫描器和写入器
                    PrintWriter out = new PrintWriter(outStream,true);
                    //给客户端发送一条问候信息
                    out.println("Hello! Enter Bye to exit.");
                    //显示客户端输入
                    boolean done = false;
                    while (!done && in.hasNextLine())
                    {
                        String line = in.nextLine();
                        out.println("Echo: " + line);
                        if (line.trim().equals("Bye"))
                        {
                            done = true;

                        }
                    }

                }

            }
        }
    }
}
