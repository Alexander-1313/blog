package ru.leverx.blog.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RequestUtilTest {

    @Autowired
    RequestUtil util;

    @Test
    void strToInt() {
        Integer expected = 55;
        Integer actual = util.strToInt("55");
        Assert.assertEquals(actual, expected);
    }

    @Test
    void isDateAvailable() {
        Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        boolean actual = util.isDateAvailable(format.format(new Date()));
        Assert.assertTrue(actual);

    }
}