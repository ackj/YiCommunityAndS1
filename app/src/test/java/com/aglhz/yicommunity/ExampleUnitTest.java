package com.aglhz.yicommunity;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        Stream<String> stream = Stream.of("chaimm", "peter", "john");

        List<String> collect = stream.filter(s -> s.length() >= 4)
                .map(s -> s+"11")
                .collect(Collectors.toList());

        for (String s : collect) {
            System.out.println(s);
        }
    }
}