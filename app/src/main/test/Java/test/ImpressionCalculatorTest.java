package test;

import org.comp2211.model.ImpressionCalculator;
import org.comp2211.model.ImpressionImporter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImpressionCalculatorTest {
    ImpressionImporter impressionImporter = new ImpressionImporter();
    ImpressionCalculator impressionCalc = new ImpressionCalculator();
    @BeforeEach
    void setUp() {
        impressionImporter.setFilePath("src\\main\\java\\org\\comp2211\\resources\\csvFiles\\impression_log.csv");
        impressionImporter.initialise();
        impressionImporter.insertRecord();
    }

    @Test
    @DisplayName("Test calculating total impressions")
    void getImpr() {
        assertEquals(486104 ,impressionCalc.getImpr());
    }

    @Test
    @DisplayName("Test calculating cost per thousands impression")
    void getCPM() {
        assertEquals("1.001957" ,String.format("%.06f",impressionCalc.getCPM())); // TODO: 08/03/2023 double check the result
    }
}