package services;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;

public interface serverServices {
    void sendResponse(Socket client, String status, String contentType, byte[] content) throws IOException;
    Path getFilePath(String path);
    String getContentType(Path filePath) throws IOException;
}
