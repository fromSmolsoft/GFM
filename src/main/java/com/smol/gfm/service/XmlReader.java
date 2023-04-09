package com.smol.gfm.service;

import com.smol.gfm.model.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.java.Log;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Log
@AllArgsConstructor
public class XmlReader {
    @NonNull
    @Setter
    private Path file;

    public DocumentObj read() throws ParserConfigurationException, IOException, SAXException {
        log.info("Reading file.");
        //init
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setNamespaceAware(true);

        DocumentBuilder db   = dbf.newDocumentBuilder();
        Document        doc  = db.parse(file.toFile());
        Node            root = doc.getDocumentElement();
        root.normalize();

        NodeList          nodeList      = root.getChildNodes();
        List<FilterEntry> filterEntries = new ArrayList<>();
        DocumentObj       documentObj   = new DocumentObj();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(XmlConst.TITLE)) {
                String text = node.getTextContent();
                documentObj.setTitle(text);
            }
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(XmlConst.ID)) {
                String text = node.getTextContent();
                documentObj.setId(text);
            }
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(XmlConst.UPDATED)) {
                String text = node.getTextContent();
                documentObj.setUpdated(text);
            }

            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(XmlConst.AUTHOR)) {
                Element entryElement = (Element) node;

                String name  = entryElement.getElementsByTagName(XmlConst.NAME).item(0).getTextContent();
                String email = entryElement.getElementsByTagName(XmlConst.EMAIL).item(0).getTextContent();

                Author author = new Author(name, email);
                documentObj.setAuthor(author);
            }

            //<entry>
            if (node.getNodeType() == Node.ELEMENT_NODE && node.getNodeName().equals(XmlConst.ENTRY)) {
                Element entryElement = (Element) node;

                // gets elements named category as NodeList > pick 1st item > gets all Attributes > gets item named "term" > returns its value as String
                String category = entryElement.getElementsByTagName(XmlConst.CATEGORY)
                        .item(0)
                        .getAttributes()
                        .getNamedItem(XmlConst.TERM)
                        .getTextContent();

                String title   = entryElement.getElementsByTagName(XmlConst.TITLE).item(0).getTextContent();
                String id      = entryElement.getElementsByTagName(XmlConst.ID).item(0).getTextContent();
                String updated = entryElement.getElementsByTagName(XmlConst.UPDATED).item(0).getTextContent();
                String content = entryElement.getElementsByTagName(XmlConst.CONTENT).item(0).getTextContent();

                //completing Java object from documentObj's data
                FilterEntry filterEntry = new FilterEntry();

                filterEntry.setCategory(category);
                filterEntry.setTitle(title);
                filterEntry.setId(id);
                filterEntry.setUpdated(updated);
                filterEntry.setContent(content);

                //<apps:property name='...' value='...'/>
                NodeList           nlProperties = entryElement.getElementsByTagName(XmlConst.APPS_PROPERTY);
                List<AppsProperty> properties   = new ArrayList<>();
                for (int j = 0; j < nlProperties.getLength(); j++) {

                    String name  = nlProperties.item(j).getAttributes().getNamedItem(XmlConst.NAME).getTextContent();
                    String value = nlProperties.item(j).getAttributes().getNamedItem(XmlConst.VALUE).getTextContent();

                    AppsProperty property = new AppsProperty();
                    property.setName(name);
                    property.setValue(value);
                    properties.add(property);
                }
                filterEntry.setProperties(properties);
                filterEntries.add(filterEntry);

            } //</entry>
        }

        documentObj.setFilterEntries(filterEntries);
        return documentObj;
    }

}

