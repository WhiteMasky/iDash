package com.delivery.util;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhoneNumberValidator {
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

    public static boolean isValidPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            log.debug("Phone number is null or empty");
            return false;
        }

        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);
            boolean isValid = phoneNumberUtil.isValidNumber(number);
            if (!isValid) {
                log.debug("Phone number {} is not valid according to libphonenumber", phoneNumber);
            }
            return isValid;
        } catch (NumberParseException e) {
            log.debug("Error parsing phone number {}: {}", phoneNumber, e.getMessage());
            return false;
        }
    }

    public static String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return phoneNumber;
        }

        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);
            if (!phoneNumberUtil.isValidNumber(number)) {
                log.debug("Cannot format invalid phone number: {}", phoneNumber);
                return phoneNumber;
            }
            return phoneNumberUtil.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (NumberParseException e) {
            log.debug("Error formatting phone number {}: {}", phoneNumber, e.getMessage());
            return phoneNumber;
        }
    }

    public static String getCountryCode(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return null;
        }

        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);
            if (!phoneNumberUtil.isValidNumber(number)) {
                log.debug("Cannot get country code for invalid phone number: {}", phoneNumber);
                return null;
            }
            return String.valueOf(number.getCountryCode());
        } catch (NumberParseException e) {
            log.debug("Error getting country code for phone number {}: {}", phoneNumber, e.getMessage());
            return null;
        }
    }

    public static String getRegionCode(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            return null;
        }

        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);
            if (!phoneNumberUtil.isValidNumber(number)) {
                log.debug("Cannot get region code for invalid phone number: {}", phoneNumber);
                return null;
            }
            return phoneNumberUtil.getRegionCodeForNumber(number);
        } catch (NumberParseException e) {
            log.debug("Error getting region code for phone number {}: {}", phoneNumber, e.getMessage());
            return null;
        }
    }
} 