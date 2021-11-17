import services.serverServicesImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

public class Server {
    public static void main(String[] args) throws IOException {
        serverServicesImpl myServer = new serverServicesImpl();
        int port = 6560;
        ServerSocket server = new ServerSocket(port);

        while(server != null) {
            Socket mySocket = server.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            StringBuilder requestBuilder = new StringBuilder();
            String currentLine;
            while (!(currentLine = reader.readLine()).isBlank()) {
                requestBuilder.append(currentLine).append("\r\n");
            }

            String request = requestBuilder.toString();
            String[] requestsLines = request.split("\r\n");
            String[] requestLine = requestsLines[0].split(" ");
//            String method = requestLine[0];
            String path = requestLine[1];
//            String version = requestLine[2];
//            String host = requestsLines[1].split(" ")[1];


            Path htmlPath = myServer.getFilePath("src/main/resources/index.html");
            Path jsonPath = myServer.getFilePath("src/main/resources/users.json");
            Path errorPath = myServer.getFilePath("src/main/resources/error.html");
            if (path.equals("/") && Files.exists(htmlPath)) {
                String contentType = myServer.getContentType(htmlPath);
                myServer.sendResponse(mySocket, "200 OK", contentType, Files.readAllBytes(htmlPath));
            } else if (path.equals("/json") && Files.exists(jsonPath)) {
                String contentType = myServer.getContentType(jsonPath);
                myServer.sendResponse(mySocket, "200 OK", contentType, Files.readAllBytes(jsonPath));
            } else {
                String contentType = myServer.getContentType(errorPath);
                myServer.sendResponse(mySocket, "200 OK", contentType, Files.readAllBytes(errorPath));
            }
            System.out.println(requestBuilder.toString());
        }
    }
}
