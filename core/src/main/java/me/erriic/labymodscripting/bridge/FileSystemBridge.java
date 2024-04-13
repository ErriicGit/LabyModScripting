package me.erriic.labymodscripting.bridge;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;

public class FileSystemBridge {

  public Path writeString(Path path, String string, OpenOption option) throws IOException {
    return Files.writeString(path, string, option);
  }

}
