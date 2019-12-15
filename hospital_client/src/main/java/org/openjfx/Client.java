package org.openjfx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;

public class Client {

    private Socket clientSocket;
    private ObjectOutputStream clientOutputStream;
    private ObjectInputStream clientInputStream;

    public void connect() {
        try {
            clientSocket = new Socket("localhost", 2525);
            clientOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            clientInputStream = new ObjectInputStream(clientSocket.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(Map<String, String> command) {
        System.out.println("Отправляю команду " + command.get("type"));
        try {
            clientOutputStream.writeObject(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> accept() {
        System.out.println("Жду ответ");
        Map<String, Object> result = null;
        try {
            result = (Map<String, Object>) clientInputStream.readObject();
            System.out.println("Получил ответ " + result);
            return result;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return result;
        }
    }
}
