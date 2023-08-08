package terraformDemo.utils;

import com.google.common.base.Optional;

import org.junit.Assert;
import org.junit.Test;

public class DateFormatterTest {

    @Test
    public void test_test() {
        Optional<Integer> possible = Optional.of(5);
        Assert.assertTrue(possible.isPresent());
        Assert.assertEquals(Integer.valueOf(5), possible.get());
    }

}
