package util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipInputStream;

import static java.net.http.HttpClient.Redirect.ALWAYS;
import static java.net.http.HttpResponse.BodyHandlers.ofInputStream;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public enum IO {;

    private static final HttpClient httpClient = HttpClient.newBuilder().followRedirects(ALWAYS).build();

    public static Path downloadFile(final URI url, final Path destinationFile) throws IOException, InterruptedException {
        final var request = HttpRequest.newBuilder(url).build();
        Files.copy(httpClient.send(request, ofInputStream()).body(), destinationFile, REPLACE_EXISTING);
        return destinationFile;
    }

    private static final byte[] BUFFER = new byte[1024];
    public static byte[] toByteArray(final ZipInputStream zis) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len; while ((len = zis.read(BUFFER)) > 0) {
            out.write(BUFFER, 0, len);
        }
        return out.toByteArray();
    }

    public static boolean deleteDirectory(final File directoryToBeDeleted) {
        final File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents == null) return true;
        for (final File file : allContents) deleteDirectory(file);
        return directoryToBeDeleted.delete();
    }
}
