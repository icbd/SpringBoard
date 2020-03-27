package org.springboard.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springboard.util.ListHelper.sample;

@Nested
class ListHelperTest {

    @Test
    void sampleTest() {
        assertNull(sample(Arrays.asList()));

        List<String> list = new ArrayList<>();
        IntStream.range(0, 10).forEach(i -> {
            list.add(RandomStringUtils.randomAlphanumeric(20));

            assertTrue(list.contains(sample(list)));
        });
    }
}
