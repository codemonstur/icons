package model;

import xmlparser.annotations.XmlName;
import xmlparser.annotations.XmlWrapperTag;

import java.util.List;

public final class MavenMetadata {

    public final String groupId;
    public final String artifactId;
    public final Versioning versioning;

    public MavenMetadata(final String groupId, final String artifactId, final Versioning versioning) {
        this.groupId = groupId;
        this.artifactId = artifactId;
        this.versioning = versioning;
    }

    public static class Versioning {
        public final String latest;
        public final String release;
        @XmlName("version")
        @XmlWrapperTag("versions")
        public final List<String> versions;

        public Versioning(final String latest, final String release, final List<String> versions) {
            this.latest = latest;
            this.release = release;
            this.versions = versions;
        }
    }
}