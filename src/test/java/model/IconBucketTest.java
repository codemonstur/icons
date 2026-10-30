package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IconBucketTest {
    @Test
    void toDir() {
        assertEquals("16x16", model.IconBucket.small.toDir());
        assertEquals("svg", model.IconBucket.scalable.toDir());
    }
}
