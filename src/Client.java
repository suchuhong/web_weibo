import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import util.Utils;

// 基于 socket 的客户端
public class Client {

    public static void socketSendAll(Socket socket, String data) throws IOException {
        OutputStream output = socket.getOutputStream();
        byte[] raw = data.getBytes();
        output.write(raw);
    }

    public static String socketReadAll(Socket socket) throws IOException {
        // 接受服务器的响应数据
        InputStream input = socket.getInputStream();
        // 指定读取的数据长度为 1024
        byte[] raw = new byte[1024];
        // read 函数会把读到的数据复制到 data 数组中去
        int size = input.read(raw);
        //noinspection UnnecessaryLocalVariable
        String data = new String(raw, 0, size);
        return data;
    }

    public static String run(String host, int port, String path) {

        String request = String.format("GET %s HTTP/1.1\r\n" +
                "Host: %s\r\n" +
                "Cookie: session_id=103a5197-4a88-4c64-87e0-c5f784718c40\r\n" +
                "\r\n", path, host);
        Utils.log("请求:\n%s", request);
        // 这是建立网络连接的方法
        // try with resource 自动释放资源
        try (Socket socket = new Socket(host, port)) {
            Utils.log("分配端口:%s", socket.getLocalPort());
            socketSendAll(socket, request);
            String response = socketReadAll(socket);
            // 输出响应的数据
            Utils.log("响应:\n%s", response);
            return response;
        } catch (IOException e) {
            Utils.log("error:" + e.toString());
            return "";
        }
    }

    public static void main(String[] args) {
        String host = "localhost";
        int port = 9000;
        String path = "/login";
        run(host, port, path);
    }
}
