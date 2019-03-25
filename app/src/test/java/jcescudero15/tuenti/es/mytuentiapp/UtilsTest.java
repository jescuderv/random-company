package jcescudero15.tuenti.es.mytuentiapp;

import org.junit.Test;

import jcescudero15.tuenti.es.mytuentiapp.utils.Utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class UtilsTest {

    @Test
    public void testCapitalizeString() {
        assertEquals("Randomco", Utils.capitalize("randomco"));
        assertNotEquals("randomco", Utils.capitalize("randomco"));
    }

    @Test
    public void testContainsMatch() {
        String expected = "Jose Carlos Escudero";
        assertThat(true, is(Utils.containsMatchText("se", expected)));
        assertThat(false, is(Utils.containsMatchText("pa", expected)));
    }

}
