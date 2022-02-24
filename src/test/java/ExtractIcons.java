import model.IconBucket;
import model.ImageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static java.nio.file.Files.write;
import static java.nio.file.StandardOpenOption.*;
import static model.IconBucket.*;
import static model.ImageType.svg;
import static model.ImageType.toImageType;
import static util.IO.*;
import static util.Logging.log;

public enum ExtractIcons {;

    private static final String
        URL_FORMAT = "https://www.jetbrains.com/intellij-repository/releases/%s/%s/%s/%s-%s.jar";
    private static final String
        GROUP_ID = "com/jetbrains/intellij/platform",
        ARTIFACT_ID = "icons",
        VERSION = "213.6777.52";


    public static void main(final String... args) throws IOException, URISyntaxException, InterruptedException {
        log("Deleting all downloaded files in 'target' and 'resources'");
        clearEverything();

        log("Making all necessary directories");
        makeDirectories();

        final URI url = new URI(String.format(URL_FORMAT, GROUP_ID, ARTIFACT_ID, VERSION, ARTIFACT_ID, VERSION));
        final Path file = Paths.get("target" + File.separator + ARTIFACT_ID + "-" + VERSION + ".jar");

        log("Downloading " + url);
        log("Storing file in " + file);
        downloadFile(url, file);

        log("Opening archive " + file + " and extracting icons");
        extractImages(file);
    }

    private static void clearEverything() {
        deleteDirectory(new File("target/"));
        deleteDirectory(new File("src/main/resources/icons/"));
    }

    private static void makeDirectories() {
        new File("target/").mkdirs();
        new File("src/main/resources/icons").mkdirs();

        new File("src/main/resources/icons/16x16-light").mkdirs();
        new File("src/main/resources/icons/16x16-dark").mkdirs();
        new File("src/main/resources/icons/32x32-light").mkdirs();
        new File("src/main/resources/icons/32x32-dark").mkdirs();

        new File("src/main/resources/icons/svg-light").mkdirs();
        new File("src/main/resources/icons/svg-dark").mkdirs();

        new File("src/main/resources/icons/other-light").mkdirs();
        new File("src/main/resources/icons/other-dark").mkdirs();
    }

    private static void extractImages(final Path fileZip) throws IOException {
        try (final ZipInputStream zis = new ZipInputStream(new FileInputStream(fileZip.toFile()))) {
            int filesWritten = 0;
            ZipEntry zipEntry; while ((zipEntry = zis.getNextEntry()) != null) {
                if (filesWritten % 100 == 0) System.out.print("\n");

                final var type = toImageType(zipEntry);
                if (type != null) {
                    final var data = toByteArray(zis);
                    final var fileName = toDestinationFile(zipEntry, type, toName(zipEntry), data);
                    write(fileName, data, CREATE, WRITE, TRUNCATE_EXISTING);
                }
                filesWritten++;
                System.out.print(".");
            }
        }
    }

    private static Path toDestinationFile(final ZipEntry entry, final ImageType type
            , final String name, final byte[] data) throws IOException {
        final var bucket = toIconBucket(type, data);
        final var lighting = toLightingSetting(entry);
        final var relativePath = bucket.toDir() + "-" + lighting + File.separator + name + "." + type.name();
        return Paths.get("src/main/resources/icons/" + relativePath);
    }

    private static IconBucket toIconBucket(final ImageType type, final byte[] data) throws IOException {
        if (type == svg) return scalable;
        try (final var in = new ByteArrayInputStream(data)) {
            final BufferedImage image = ImageIO.read(in);
            final int width = image.getWidth();
            final int height = image.getHeight();
            if (width == 16 && height == 16) return small;
            if (width == 32 && height == 32) return large;
        }
        return other;
    }

    private static String toLightingSetting(final ZipEntry entry) {
        final String zipFileName = entry.getName();
        final boolean darkPng = zipFileName.endsWith("_dark.png");
        final boolean darkSvg = zipFileName.endsWith("_dark.svg");
        return darkPng || darkSvg ? "dark" : "light";
    }

    private static String toName(final ZipEntry entry) {
        final String zipFileName = entry.getName();
        final int dirOffset = zipFileName.lastIndexOf('/') + 1;
        final int atOffset = zipFileName.lastIndexOf("@2x");
        final int unOffset = zipFileName.lastIndexOf("_dark.");
        final int peOffset = zipFileName.lastIndexOf('.');

        return zipFileName.substring(dirOffset, toSmallestOffset(atOffset, unOffset, peOffset));
    }

    private static int toSmallestOffset(final int... offsets) {
        int smallestNotNegative = Integer.MAX_VALUE;
        for (final int offset : offsets) {
            if (offset != -1) smallestNotNegative = Math.min(smallestNotNegative, offset);
        }
        return smallestNotNegative;
    }

}
