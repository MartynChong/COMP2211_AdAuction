package test;

import org.comp2211.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClickCalculatorTest {
    ClickImporter clickImporter = new ClickImporter();
    ImpressionImporter impressionImporter = new ImpressionImporter();
//    ServerLogImporter serverLogImporter = new ServerLogImporter();
    ClickCalculator clickCalc = new ClickCalculator();
//    ImpressionCalculator impressionCalc = new ImpressionCalculator();
//    ServerLogCalculator serverLogCalc = new ServerLogCalculator();

    @BeforeEach
    void setup() {
        clickImporter.setFilePath("src\\main\\java\\org\\comp2211\\resources\\csvFiles\\click_log.csv");
        impressionImporter.setFilePath("src\\main\\java\\org\\comp2211\\resources\\csvFiles\\impression_log.csv");
//        serverLogImporter.setFilePath("src\\main\\java\\org\\comp2211\\resources\\csvFiles\\server_log.csv");
        clickImporter.initialise();
        clickImporter.insertRecord();
        impressionImporter.initialise();
        impressionImporter.insertRecord();
//        serverLogImporter.initialise();
//        serverLogImporter.insertRecord();
    }

    @Test
    @DisplayName("Test calculating total clicks")
    void testTotalClicks() {
        assertEquals(23923 ,clickCalc.getTotalClicks());
    }

    @Test
    @DisplayName("Test calculating unique clicks")
    void testUniqueClicks() {
        assertEquals(23806 ,clickCalc.getUniqueClicks());
    }

    @Test
    @DisplayName("Test calculating total costs")
    void testTotalCost() {
        assertEquals(118097.921875 ,clickCalc.getTotalCost());
    }

    @Test
    @DisplayName("Test calculating cost per click")
        void testCPC() {
        assertEquals(4.92 ,clickCalc.getCPC()); // TODO: 08/03/2023 double check the result
    }

    @Test
    @DisplayName("Test calculating click through rate")
    void testCTR() {
        assertEquals(4.921374 , String.format("%.06f", clickCalc.getCTR()));
    }

}