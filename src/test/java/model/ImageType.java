package model;

import java.util.zip.ZipEntry;

public enum ImageType {
    png, svg;

    public static ImageType toImageType(final ZipEntry entry) {
        final String zipFileName = entry.getName();
        if (zipFileName.endsWith(".png")) return png;
        if (zipFileName.endsWith(".svg")) return svg;
        return null;
    }

}
