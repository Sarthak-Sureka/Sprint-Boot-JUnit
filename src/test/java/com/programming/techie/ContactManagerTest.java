package com.programming.techie;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactManagerTest {

    ContactManager contactManager;

    @BeforeAll
    @DisplayName("Before All Annotation Test")
    //public static void setupAll(){ - Since TestInstance annotation is defined above
    public void setupAll(){
        System.out.println("Should Print Before All Tests");
    }

    @BeforeEach
    @DisplayName("Before Each Annotation Test")
    public void setup(){
        System.out.println("Should Print Before Each Tests");
        contactManager = new ContactManager();
    }

    @Test
    @DisplayName("Should Create Contact")
    @Disabled
    public void shouldCreateContact(){
        System.out.println("Inside Create Contact");
        contactManager.addContact("John","Doe","0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                .filter(contact -> contact.getFirstName().equals("John") &&
                        contact.getLastName().equals("Doe") &&
                        contact.getPhoneNumber().equals("0123456789"))
                .findAny()
                .isPresent());
    }

    @Test
    @DisplayName("Should Not Create Contact When First Name is Null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull(){
        System.out.println("Inside First Name Exception");
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact(null,"Doe","0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Last Name is Null")
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull(){
        System.out.println("Inside Last Name Exception");
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John",null,"0123456789");
        });
    }

    @Test
    @DisplayName("Should Not Create Contact When Phone Number is Null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull(){
        System.out.println("Inside Phone Number Exception");
        Assertions.assertThrows(RuntimeException.class, () -> {
            contactManager.addContact("John","Doe",null);
        });
    }

    @AfterEach
    @DisplayName("After Each Annotation Test")
    public void tearDown(){
        System.out.println("Should Print After Each Test");
    }

    @AfterAll
    @DisplayName("After All Annotation Test")
    //public static void tearDownAll(){
    public void tearDownAll(){
        System.out.println("Should Print After All Test");
    }

    //Conditional Execution Example
    @Test
    @DisplayName("Should Create Contact Only on Windows OS")
    @EnabledOnOs(value = OS.WINDOWS, disabledReason = "Enabled only on Windows OS")
    public void shouldCreateContactOnlyOnWindows(){
        System.out.println("Inside Create Contact Enabled in Windows");
        contactManager.addContact("John","Doe","0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                                            .filter(contact -> contact.getFirstName().equals("John") &&
                                                    contact.getLastName().equals("Doe") &&
                                                    contact.getPhoneNumber().equals("0123456789"))
                                            .findAny()
                                            .isPresent());
    }

    //Conditional Execution Example
    @Test
    @DisplayName("Should Create Contact Only on MAC OS")
    @DisabledOnOs(value = OS.MAC, disabledReason = "Disabled only on MAC OS")
    public void shouldCreateContactOnlyOnMAC(){
        System.out.println("Inside Create Contact Disabled in MAC");
        contactManager.addContact("John","Doe","0123456789");
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
        Assertions.assertTrue(contactManager.getAllContacts().stream()
                                            .filter(contact -> contact.getFirstName().equals("John") &&
                                                    contact.getLastName().equals("Doe") &&
                                                    contact.getPhoneNumber().equals("0123456789"))
                                            .findAny()
                                            .isPresent());
    }

    //Nested Test Class Examples
    @Nested
    class RepeatedNestedTest{
        //Repeated Test Example
        @DisplayName("Repeat Contact Creations Test 5 times")
        @RepeatedTest(value = 5, name = "Repeating Contact Creation Test {currentRepetition} of {totalRepetitions}")
        public void shouldTestCreateContactRepeatedly(){
            System.out.println("Inside Create Contact Repeatedly");
            contactManager.addContact("John","Doe","0123456789");
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1,contactManager.getAllContacts().size());
        }
    }

    @Nested
    class ParameterizedNestedTest {
        //Parameterized Test Example
        @DisplayName("Contact Creation test using Parameterized Test and Value Source")
        @ParameterizedTest
        @ValueSource(strings = {"0123456789", "0123456789", "+0123456789"})
        public void shouldTestCreateContactUsingValueSource(String phoneNumber){
            System.out.println("Inside Create Contact Parameterized Test Using Value Source");
            contactManager.addContact("John","Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1,contactManager.getAllContacts().size());
        }

        //Parameterized Test Example
        @DisplayName("Contact Creation test using Parameterized Test and CSV Source")
        @ParameterizedTest
        @CsvSource({"0123456789", "0123456789", "+0123456789"})
        public void shouldTestPhoneNumberFormatUsingCSVSource(String phoneNumber) {
            System.out.println("Inside Create Contact Parameterized Test Using CSV Source");
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }

        //Parameterized Test Example
        @DisplayName("Contact Creation test using Parameterized Test and CSV File Source")
        @ParameterizedTest
        @CsvFileSource(resources = "/data.csv")
        public void shouldTestPhoneNumberFormatUsingCSVFileSource(String phoneNumber) {
            System.out.println("Inside Create Contact Parameterized Test Using CSV File Source");
            contactManager.addContact("John", "Doe", phoneNumber);
            Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
            Assertions.assertEquals(1, contactManager.getAllContacts().size());
        }
    }

    //Parameterized Test Example
    @DisplayName("Contact Creation test using Parameterized Test and Method Source")
    @ParameterizedTest
    @MethodSource("phoneNumberList")
    public void shouldTestPhoneNumberFormatUsingMethodSource(String phoneNumber){
        System.out.println("Inside Create Contact Parameterized Test Using Method Source");
        contactManager.addContact("John","Doe", phoneNumber);
        Assertions.assertFalse(contactManager.getAllContacts().isEmpty());
        Assertions.assertEquals(1,contactManager.getAllContacts().size());
    }

    //This method specifically created for testing Method Source annotation of Parameterized Test
    private static List<String> phoneNumberList(){
        return Arrays.asList("0123456789","0123456789","+0123456789");
    }
}
