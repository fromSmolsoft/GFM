package com.smol.gfm.service;


import com.smol.gfm.model.AppsProperty;
import com.smol.gfm.model.DocumentObj;
import com.smol.gfm.model.FilterEntry;
import com.smol.gfm.model.XmlConst;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log()
@Data
public class XmlWriter {

    @NonNull
    @Setter
    DocumentObj documentObj;
    Document      doc;
    Element       root;
    List<Element> headerList;
    @NonNull
    @Setter
    Path exportFile;

    /** Creates empty new document */
    private void createNewDocument() throws ParserConfigurationException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        /* prevents vulnerability to XXE attacks*/
        dbf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.newDocument();
        doc.setXmlStandalone(true);

    }

    /**
     * Inserts following <br> "&lt;feed xmlns=&#39;http://www.w3.org/2005/Atom&#39; xmlns:apps=&#39;http://schemas.google.com/apps/2006&#39;&gt; "
     */
    private void insertRootAtribute() {
        root = doc.createElement(XmlConst.FEED);
        root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://www.w3.org/2005/Atom");
        root.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:apps", "http://schemas.google.com/apps/2006");

    }

    /** Inserts childless header elements */
    private void insertOneLineHeaders() {
        /* Creates elements */
        Element titleElement   = doc.createElement(XmlConst.TITLE);
        Element idElement      = doc.createElement(XmlConst.ID);
        Element updatedElement = doc.createElement(XmlConst.UPDATED);
        Element authorElement  = doc.createElement(XmlConst.AUTHOR);
        /* Child elements */
        Element authorNameElement  = doc.createElement(XmlConst.NAME);
        Element authorEmailElement = doc.createElement(XmlConst.EMAIL);
        /* Sets text to elements */
        Text titleText   = doc.createTextNode(documentObj.getTitle());
        Text idText      = doc.createTextNode(documentObj.getId());
        Text updatedText = doc.createTextNode(documentObj.getUpdated());

        Text authorNameText  = doc.createTextNode(documentObj.getAuthor().getName());
        Text authorEmailText = doc.createTextNode(documentObj.getAuthor().getEmail());

        /* appends text and childs to the lements*/
        titleElement.appendChild(titleText);
        idElement.appendChild(idText);
        updatedElement.appendChild(updatedText);
        authorNameElement.appendChild(authorNameText);
        authorEmailElement.appendChild(authorEmailText);

        authorElement.appendChild(authorNameElement);
        authorElement.appendChild(authorEmailElement);
        /* appends elements to the root element */
        root.appendChild(titleElement);
        root.appendChild(idElement);
        root.appendChild(updatedElement);
        root.appendChild(authorElement);
    }

    /** Inserts main data entries */
    private void insertEntries() {
        //Todo insertEntries
        //loop all entries
        for (FilterEntry entry : documentObj.getFilterEntries()
        ) {
            createEntry(entry);
        }


    }

    /* Creates single Entry*/
    private void createEntry(FilterEntry entry) {
        Element entryElement = doc.createElement(XmlConst.ENTRY);
        /* each entry -> add "headers" element */
        Element categoryElement = doc.createElement(XmlConst.CATEGORY);
        Element titleElement    = doc.createElement(XmlConst.TITLE);
        Element idElement       = doc.createElement(XmlConst.ID);
        Element updatedElement  = doc.createElement(XmlConst.UPDATED);
        Element contentElement  = doc.createElement(XmlConst.CONTENT);

        Attr categoryAttr = doc.createAttribute(XmlConst.TERM);
        categoryAttr.setValue(entry.getCategory());

        /* Sets text to elements */
        Text titleText   = doc.createTextNode(entry.getTitle());
        Text idText      = doc.createTextNode(entry.getId());
        Text updatedText = doc.createTextNode(entry.getUpdated());
        Text contentText = doc.createTextNode(entry.getContent());

        /* Append text and children to the elements*/
        categoryElement.setAttributeNode(categoryAttr);
        titleElement.appendChild(titleText);
        idElement.appendChild(idText);
        updatedElement.appendChild(updatedText);
        contentElement.appendChild(contentText);

        /* Append header elements */
        entryElement.appendChild(categoryElement);
        entryElement.appendChild(titleElement);
        entryElement.appendChild(idElement);
        entryElement.appendChild(updatedElement);
        entryElement.appendChild(contentElement);

        /* each entry -> propertyList each p-> element */
        for (AppsProperty p : entry.getProperties()
        ) {
            createProperty(p, entryElement);
        }
        /* append element to its parent */
        root.appendChild(entryElement);
    }

    /** Creates single property &lt;apps:property name=... value=... /&gt; */
    private void createProperty(AppsProperty p, Element entryElement) {
        /* Create element */
        Element elementProperty = doc.createElement(XmlConst.APPS_PROPERTY);

        /* create attributes */
        Attr name  = doc.createAttribute(XmlConst.NAME);
        Attr value = doc.createAttribute(XmlConst.VALUE);
        name.setValue(p.getName());
        value.setValue(p.getValue());

        /* set attr to the element */
        elementProperty.setAttributeNode(name);
        elementProperty.setAttributeNode(value);
        /* append element to its parent */
        entryElement.appendChild(elementProperty);
    }

    /** Closes root element. Used after all data were inserted. */
    private void closeRoot() {
        /* appends root element to the document */
        doc.appendChild(root);
    }


    /** Transforms and saves files to specified filePath */
    public void transformDocToFile() throws TransformerException {

        TransformerFactory tf = TransformerFactory.newInstance();
        /* prevents vulnerability to XXE attacks*/
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        tf.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        Transformer transformer = tf.newTransformer();
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);

        StreamResult result = new StreamResult(exportFile.toFile());
        transformer.transform(source, result);
        log.info("Saved to: " + exportFile.toFile());
    }

    /** Calls all individual document-building methods, which results in creating final document and exporting it */
    @SneakyThrows
    public void completeDocument() {
        createNewDocument();
        insertRootAtribute();
        insertOneLineHeaders();
        insertEntries();
        closeRoot();

        transformDocToFile();
    }

}
//todo post process xml to replaces " for ' if needed