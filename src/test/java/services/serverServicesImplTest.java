package services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class serverServicesImplTest {
    ServerSocket server;
    Socket clientSocket;
    BufferedReader reader;
    StringBuilder requestBuilder;
    String line;

    @BeforeEach
    void setUp() throws IOException {
        server = new ServerSocket(6550);
        clientSocket = server.accept();
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        requestBuilder = new StringBuilder();
    }

    @Test
    @DisplayName("To check if the filePath returned is what is expected")
    void getFilePath() throws IOException {

        while (!(line = reader.readLine()).isBlank()) {
            requestBuilder.append(line).append("\r\n");
        }
        String request = requestBuilder.toString();
        String[] requestsLines = request.split("\r\n");
        String[] requestLine = requestsLines[0].split(" ");
        String path = requestLine[0];

        final String expectedResult = "HTTP/1.1";
        final String actualResult = requestLine[2];

        assertEquals(expectedResult, actualResult);
        server.close();
        clientSocket.close();
    }

    @Test
    @DisplayName("To check for the content type of the filepath")
    void ShouldTestTheContentType() throws IOException {
        final String expectedResult = "text/html";
        final String actualResult = Files.probeContentType(Path.of("src/main/resources/index.html"));

        assertEquals(expectedResult, actualResult);
    }
}


