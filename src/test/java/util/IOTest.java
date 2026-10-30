package util;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.zip.ZipInputStream;

import static org.junit.jupiter.api.Assertions.*;

class IOTest {
    @Test
    void deleteDirectoryNonExistent() {
        assertTrue(IO.deleteDirectory(new File("does-not-exist-12345")));
    }

    @Test
    void deleteDirectoryRemovesEmptyDir() throws Exception {
        var root = new File("target/test-del-empty");
        root.mkdirs();
        IO.deleteDirectory(root);
        assertFalse(root.exists());
    }

    @Test
    void toByteArray() throws Exception {
        var zis = new ZipInputStream(new ByteArrayInputStream(new byte[0]));
        assertEquals(0, IO.toByteArray(zis).length);
    }
}
