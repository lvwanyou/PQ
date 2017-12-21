# PQ

Here is  a chat software. What the point is that all xmls and javas are designed by the naive codes without any tripartite plug-in units.

先看看socket通信基础：
所谓socket通常也称作"套接字"，用于描述IP地址和端口，是一个通信链的句柄。应用程序通常通过"套接字"向网络发出请求或者应答网络请求。 
Socket和ServerSocket类库位于java.net包中。ServerSocket用于服务器端，Socket是建立网络连接时使用的。在连接成功时，应用程序两端都会产生一个Socket实例，操作这个实例，完成所需的会话。
服务器：
使用ServerSocket监听指定的端口，等待客户连接请求，客户连接后，会话产生；在完成会话后，关闭连接。 

客户端：
使用Socket对网络上某一个服务器的某一个端口发出连接请求，一旦连接成功，打开会话；会话完成后，关闭Socket。

0~1023的端口号为系统所保留，例如http服务的端口号为80,telnet服务的端口号为21,ftp服务的端口号为23, 所以我们在选择端口号时，最好选择一个大于1023的数以防止发生冲突。

简单的Client/Server示例：

public class Client {
	public static void main(String[] args) throws Exception {
		//向本机的5469端口发出客户请求
		Socket socket=new Socket(InetAddress.getLocalHost(),5469);
		//由Socket对象得到输入流，并构造相应的BufferedReader对象
		BufferedReader is=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//由Socket对象得到输出流，并构造PrintWriter对象
		PrintWriter os=new PrintWriter(socket.getOutputStream());
		//由系统标准输入设备构造BufferedReader对象
		BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
		while(true){
			String str=sin.readLine();//从系统标准输入读入一字符串
			os.println(str);
			os.flush(); //刷新输出流，使Server马上收到该字符串
			
			String s=is.readLine();
			System.out.println("Server : "+s);//在标准输出上打印从Server读入的字符串
			if(str.equals("end")){
				break;
			}
		}
		is.close();//关闭Socket输入流
		os.close();//关闭Socket输出流
		socket.close();//关闭Socket
	}
}


public class MyServer {
	public static void main(String[] args) throws Exception{
		ServerSocket server=new ServerSocket(5469);//创建一个ServerSocket在端口5469监听客户请求
		Socket client=server.accept();//使用accept()阻塞等待客户请求
		BufferedReader is=new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter os=new PrintWriter(client.getOutputStream());
		BufferedReader sin=new BufferedReader(new InputStreamReader(System.in));
		while(true){
			String str=is.readLine();
			System.out.println("Client : "+str);//在标准输出上打印从Client读入的字符串
			
			os.println(sin.readLine());
			os.flush();//刷新输出流，使Client马上收到该字符串
			if(str.equals("end")){
				break;
			}
		}
		is.close();
		os.close();
		client.close();
		server.close();
	}
}


  <link rel="icon" type="image/x-icon" class="js-site-favicon" href="https://assets-cdn.github.com/favicon.ico">
