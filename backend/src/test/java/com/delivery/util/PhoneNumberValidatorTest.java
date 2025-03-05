package com.delivery.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PhoneNumberValidatorTest {

    @Test
    void testValidPhoneNumber() {
        assertTrue(PhoneNumberValidator.isValidPhoneNumber("+8613812345678"));
        assertTrue(PhoneNumberValidator.isValidPhoneNumber("+61412345678"));
        assertFalse(PhoneNumberValidator.isValidPhoneNumber("invalid"));
        assertFalse(PhoneNumberValidator.isValidPhoneNumber(""));
        assertFalse(PhoneNumberValidator.isValidPhoneNumber(null));
    }

    @Test
    void testFormatPhoneNumber() {
        assertEquals("+8613812345678", PhoneNumberValidator.formatPhoneNumber("+8613812345678"));
        assertEquals("+61412345678", PhoneNumberValidator.formatPhoneNumber("+61412345678"));
        assertEquals("invalid", PhoneNumberValidator.formatPhoneNumber("invalid"));
        assertEquals("", PhoneNumberValidator.formatPhoneNumber(""));
        assertNull(PhoneNumberValidator.formatPhoneNumber(null));
    }

    @Test
    void testGetCountryCode() {
        assertEquals("86", PhoneNumberValidator.getCountryCode("+8613812345678"));
        assertEquals("61", PhoneNumberValidator.getCountryCode("+61412345678"));
        assertNull(PhoneNumberValidator.getCountryCode("invalid"));
        assertNull(PhoneNumberValidator.getCountryCode(""));
        assertNull(PhoneNumberValidator.getCountryCode(null));
    }

    @Test
    void testGetRegionCode() {
        assertEquals("CN", PhoneNumberValidator.getRegionCode("+8613812345678"));
        assertEquals("AU", PhoneNumberValidator.getRegionCode("+61412345678"));
        assertNull(PhoneNumberValidator.getRegionCode("invalid"));
        assertNull(PhoneNumberValidator.getRegionCode(""));
        assertNull(PhoneNumberValidator.getRegionCode(null));
    }
} 