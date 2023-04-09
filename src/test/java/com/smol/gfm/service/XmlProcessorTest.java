package com.smol.gfm.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class XmlProcessorTest {

  private XmlProcessor xmlProcessor;
   private XmlReader xmlReader;
    private Path file;

    @BeforeAll
    static void beforeAll() {

    }

    @BeforeEach
    void setUp() {
        xmlProcessor = new XmlProcessor();
        file = Path.of("C:\\Users\\Martin Smolek\\Development\\IdeaProjects\\Apps\\GmailFilterManager\\Gmail Filters\\mailFilters.xml" );
    }


    @AfterEach
    void tearDown() {
    }

    @Test
    void splittingString() {
        String input = "someEmail@gmail.com | otherEmail@noname.uk.com OR ororor@gmail.com ";
        String[] expectedOutput ={"someEmail@gmail.com","otherEmail@noname.uk.com","ororor@gmail.com"};
        assertArrayEquals(expectedOutput,xmlProcessor.splitAndTrimString(input));
    }
}
