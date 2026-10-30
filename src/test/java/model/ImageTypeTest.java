package model;

import org.junit.jupiter.api.Test;
import java.util.zip.ZipEntry;

import static org.junit.jupiter.api.Assertions.*;

class ImageTypeTest {
    @Test
    void toImageType() {
        assertEquals(ImageType.png, ImageType.toImageType(new ZipEntry("a.png")));
        assertEquals(ImageType.svg, ImageType.toImageType(new ZipEntry("a.svg")));
        assertNull(ImageType.toImageType(new ZipEntry("a.txt")));
    }
}
