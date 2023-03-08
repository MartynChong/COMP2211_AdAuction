package test;

import org.comp2211.model.ServerLogCalculator;
import org.comp2211.model.ServerLogImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerLogCalculatorTest {
    ServerLogCalculator serverLogCalc = new ServerLogCalculator();
    ServerLogImporter serverLogImporter = new ServerLogImporter();

    @BeforeEach
    void setUp() {
        serverLogImporter.setFilePath("src\\main\\java\\org\\comp2211\\resources\\csvFiles\\server_log.csv");
        serverLogImporter.initialise();
        serverLogImporter.insertRecord();

    }

    @Test
    @DisplayName("Test calculating total bounce")
    void getBounce() {
        assertEquals(8665 ,serverLogCalc.getBounce());
    }

    @Test
    @DisplayName("Test calculating total conversion")
    void getConver() {
        assertEquals(2026 ,serverLogCalc.getConver());
    }

    @Test
    @DisplayName("Test calculating bounce rate")
    void getBounceRate() {
        assertEquals(36.220375061035156 ,serverLogCalc.getBounceRate());
    }

    @Test
    @DisplayName("Test calculating cost per acquisition")
    void getCPA() {
        assertEquals(58.29 ,serverLogCalc.getCPA()); // TODO: 08/03/2023 double check the result
    }
}