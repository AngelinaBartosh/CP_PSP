package sample;

import java.io.*;
import java.net.Socket;

public class Client {
    Socket socket = new Socket("localhost", 8080);

    public InputStream inputStream;
    public OutputStream outputStream;
    public ObjectInputStream ois;
    public ObjectOutputStream oos;
    public DataInputStream dis;
    public DataOutputStream dos;

    public Client() throws IOException {
        this.inputStream = this.socket.getInputStream ();
        this.outputStream = this.socket.getOutputStream ();
        this.oos = new ObjectOutputStream (outputStream);
        this.ois = new ObjectInputStream (inputStream);
        this.dis = new DataInputStream (inputStream);
        this.dos = new DataOutputStream (outputStream);
    }
}
