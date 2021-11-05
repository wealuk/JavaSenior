package com.atguigu.java1;

import org.junit.Test;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**---------------TCP编程的重点归纳在下方的总结和notes中
 * 实现TCP的网络编程
 * 例题3：从客户端发送文件给服务端，服务端保存到本地。并返回“发送成功”给客户端。并关闭相应的连接。
 *
 * 还可以做下以下练习：1.服务端读取图片并发送给客户端，客户端保存图片到本地
 *                  2.客户端给服务端发送文本，服务端会将文本转成大写在返回给客户端。
 *
 *  三个例题TCP的网络编程的总结：（以客户端发起请求为例）
 *      客户端：1.创建指明服务器端的IP和端口号的Socket对象----服务端可以通过ServerSocket对象的accept()获取该Socket
 *      notes:第一步极为关键，创建Socket对象就相当于连接到服务器。如果服务器未开启或是不存在，那么第一步就会报异常。
 *        而服务端等待客户端传入socket，用accept()接收socket，两个共同的socket就可以获取输入输出流来互相传数据。
 *             2.客户端可以通过socket的getOutputStream()获取输出流，通过该流可以写出数据。
 *                          ---服务端通过接收到的Socket对象获取与之对应的输入流，用于读取客户端所输出的数据。
 *             3.写出数据
 *             4.关闭资源(流、socket)
 *
 *      服务端：1.创建ServerSocket对象，指明服务器的端口号(IP就是本机地址)
 *             2.通过ServerSocket对象的accept()获取客户端的Socket对象
 *             3.通过Socket就可以获取输入流，通过输入流就可以读取传过来的数据。
 *             4.读取数据。
 *             5.关闭资源。
 *       注：1.如果服务器与客户端只进行一次数据传输，那么传输后就是文件末尾资源关闭操作，在服务端的read()就不会阻塞。(上面的流程)
 *          如果传输一次后还有别的传输操作，就像下面的服务器还会传输数据到客户端，那么服务器第一次的read()就会阻塞，
 *          一直等待客户端的数据传输。所有，像这种情况，我们需要在客户端第一次传输完数据后，调用socket.shutdownOutput(),
 *          来关闭数据的传输。这样，我们才能继续进行后续的数据传输。
 *          2.数据传输的核心就是Socket对象(客户端创建，服务端获取)，通过socket我们可以获取相对应的输入、输出流，一边
 *          用输出流传输数据，另一边就可以用输入流读取传过来的数据。服务端也可以主动获取socket，然后得到输出流给客户端
 *          传输数据，而客户端也可以获取相应的输入流来读取服务端传过来的数据。故两边都可以相互传输数据。
 *
 *    notes：TCP的网络编程，一定要先开启服务端，再开启客户端。否则客户端创建Socket对象时就会因为匹配不到服务器而报异常。
 *     相当于创建Socket对象就是连接到服务器，而服务器也就能通过accept()获取客户端的socket。两个共有的socket
 *     就可以获取输入输出流互相传递数据。(不论是客户端传到服务端，还是服务端传到客户端，都需要先开启服务端，然后让客户端
 *     创建Scoket对象连接到服务端，服务端再获取客户端的socket，然后才能进行数据的传输)
 * @author shkstart
 * @create 2021-05-12 18:57
 */
public class InterTest04 {
    /*
    这里涉及到的异常，应该使用try-catch-finally处理
     */
    @Test
    public void client() throws IOException {
        //1.创建Socket对象，指明服务器端的IP和端口号
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"),9090);
        //2.获取一个输出流
        OutputStream os = socket.getOutputStream();
        //3.创建一个输入流，用于从磁盘中读取要发送的文件到内存中。
        FileInputStream fis = new FileInputStream(new File("20210212135721_1.jpg"));
        //4.读取并发送数据
        byte[] buffer = new byte[1024];
        int len;
        while((len = fis.read(buffer)) != -1){
            os.write(buffer,0,len);
        }
        //5.关闭数据的输出
        socket.shutdownOutput();
        //notes:这一步很关键，没有整个程序无法运行成功。客户端没有关闭数据的传输的话，由于第5步的read()方法是阻塞
        //的方法，所以读取到-1时图片传输完了，但是由于在客户端没有关闭数据的传输，我们就不知道是否还会有数据传输过来，
        //那么就会一直在服务端的while循环等待客户端传输数据。
        //总结：所以，我们客户端必须在传完图片后，指明关闭数据的输出。那么，在服务端的read()方法才会停止阻塞，程序才能继续进行。

        //6.接收来自于服务器端的数据，并显示在控制台上
        InputStream is = socket.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer1 = new byte[20];
        int len1;
        while((len1 = is.read(buffer1)) != -1){
            baos.write(buffer1,0,len1);
        }

        System.out.println(baos.toString());

        //7.关闭资源
        fis.close();
        os.close();
        socket.close();
        baos.close();
        is.close();

    }

    @Test
    public void server() throws IOException {
        //1.创建服务器端的ServerSocket,指明自己的端口号
        ServerSocket ss = new ServerSocket(9090);
        //2.调用accept()表示接收来自于客户端的socket
        Socket socket = ss.accept();
        //3.获取输入流，读取客户端发生的数据
        InputStream is = socket.getInputStream();
        //4.创建输出流，用于保存发送过来的数据
        FileOutputStream fos = new FileOutputStream(new File("20210212135721_14.jpg"));
        //5.读取和写出的过程
        byte[] buffer = new byte[1024];
        int len;
        while((len = is.read(buffer)) != -1){//客户端没有关闭数据的输出，read()方法就会阻塞一直等待客户端传输数据，
            fos.write(buffer,0,len);//那么程序就会停在while循环出不去。所以客户端socket.shutdowmOutput()很关键
        }

        //6.服务器端给予客户端反馈
        OutputStream os = socket.getOutputStream();
        os.write("收到了！嘿嘿".getBytes());

        //7.关闭资源
        fos.close();
        is.close();
        socket.close();
        ss.close();
        os.close();

    }
}
