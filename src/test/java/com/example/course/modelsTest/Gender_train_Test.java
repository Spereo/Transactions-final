package com.example.course.modelsTest;

import com.example.course.models.Gender_train;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Gender_train_Test {
    Gender_train gender = new Gender_train(1, "0");

    @Test
    public void testGender() { assertNotNull(gender); }

    @Test
    public void testGetCustomerId() { assertEquals(1, gender.getCustomer_id()); }

    @Test
    public void testGetGender() { assertEquals("0", gender.getGender()); }

    @Test
    public void testSetGender() {
        gender.setGender("1");
        assertEquals("1", gender.getGender());
    }

    @Test
    public void testSetCustomerId() {
        gender.setCustomer_id(8);
        assertEquals(8, gender.getCustomer_id());
    }

}
