package tenksteps.publicapi;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

class CryptoHelper {

  static String publicKey() throws IOException {
    return read("public_key.pem");
  }

  static String privateKey() throws IOException {
    return read("private_key.pem");
  }

  private static String read(String file) throws IOException {
    Path path = Paths.get("public-api", file);
    if (!path.toFile().exists()) {
      path = Paths.get("..", "public-api", file);
    }
    return Files.readAllLines(path, StandardCharsets.UTF_8)
      .stream()
      .filter(line -> !line.startsWith("----"))
      .collect(Collectors.joining());
  }
}
