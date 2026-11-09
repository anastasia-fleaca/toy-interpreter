package interpreter.model.value;

import interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringValueTest {
    @Test
    void equalStringValues()
    {
        StringValue string1 = new StringValue("test.in");
        StringValue string2 = new StringValue("test.in");

        assertEquals(string1, string2);
    }

    @Test
    void equalHashCodes()
    {
        StringValue string1 = new StringValue("test.in");
        StringValue string2 = new StringValue("test.in");

        assertEquals(string1.hashCode(), string2.hashCode());
    }

    @Test
    void defaultStringIsEmptyString()
    {
        assertEquals(new StringValue(""), new StringType().defaultValue());
    }
}
