package com.smol.gfm.service;

import com.smol.gfm.model.XmlConst;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
@Log
class XmlWriterTest {
    XmlWriter xmlWriter;
    XmlReader xmlReader;

    @SneakyThrows
    @BeforeEach
    void setUp() {
        Path importPath = Path.of(XmlConst.PATH_MAIL_FILTERS_XML);
        Path exportPath = Path.of(XmlConst.PATH_EX_MAIL_FILTERS_XML);
        xmlReader = new XmlReader(importPath);
        xmlWriter = new XmlWriter(xmlReader.read(),exportPath);

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void completionOfDocument() {
        xmlWriter.completeDocument();
       log.info( xmlWriter.doc.toString());
    }
}