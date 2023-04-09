package com.smol.gfm.service;

import com.smol.gfm.model.*;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.util.*;
import java.util.stream.Collectors;

@Log
@NoArgsConstructor
public class XmlProcessor {
    DocumentObj oldDocument;

    /** Extracts property list from entry without from property */
    private static List<AppsProperty> getPropertyList(FilterEntry entry1) {
        return entry1.getProperties()
                .stream()
                .filter(appsProperty -> !appsProperty.getName().equals(XmlConst.FROM))
                .toList();
    }

    /**
     * Method scans document.
     * @return DocumentObj, new Document
     */
    public DocumentObj processDoc(DocumentObj oldDocument) {
        this.oldDocument = oldDocument;
        DocumentObj newDocument = new DocumentObj("Load file to proceed.");

        if (oldDocument != null) {
            newDocument.setFeed(oldDocument.getFeed());
            newDocument.setTitle(oldDocument.getTitle());
            newDocument.setUpdated(oldDocument.getUpdated());
            newDocument.setAuthor(oldDocument.getAuthor());

            List<FilterEntry> entryList = mergeDuplicateEntries(groupDupeEntries(oldDocument.getFilterEntries()));
            newDocument.setFilterEntries(entryList);

            newDocument.setId(updateIdsElement(newDocument));

            log.info("Document processed. ");
        }

        return newDocument;
    }

    /** Generates new element <'id>...</'id> for document header */
    protected String updateIdsElement(DocumentObj newDocument) {
        StringJoiner ids = new StringJoiner(",", XmlConst.ID_TAG_HEADER, "");
        for (FilterEntry entry : newDocument.getFilterEntries()) {
            try {
                ids.add(entry.getId().split(XmlConst.ID_TAG_ENTRY)[1]);
            } catch (IndexOutOfBoundsException ignored) {
                /* catching exp here just in case there's no id, to not stop app  */
            }
        }
        return ids.toString();
    }

    /** Method groups duplicate 'app:property' lists excluding app:property' 'name' 'from' */
    @SuppressWarnings("squid:S3776")
    public Map<FilterEntry, Set<FilterEntry>> groupDupeEntries(List<FilterEntry> oldEntryList) {
        /* set to evidence already compared entries */
        Set<FilterEntry> allDupesSet = new HashSet<>();

        /* map to evidence dupes in 1 to many relationship  */
        Map<FilterEntry, Set<FilterEntry>> duplicateMap = new HashMap<>();

        /* iterates entries 1 = map keys  */
        for (FilterEntry entry1 : oldEntryList) {
            /* evidence for entry1's duplicities */
            Set<FilterEntry> dupeEntrySet = new HashSet<>();
            /* skip entries marked as duplicity previously */
            if (!allDupesSet.contains(entry1)) {
                List<AppsProperty> properties1 = getPropertyList(entry1);
                int                sizeP1      = properties1.size();
                duplicateMap.put(entry1, dupeEntrySet);

                /* iterates entries 2 to add dupes into map value which is a <set> */
                for (FilterEntry entry2 : oldEntryList) {

                    /* entry2 hasn't been used as a key */
                    if (!duplicateMap.containsKey(entry2)) {

                        /* optional = if entry2 hasn't got 100% dupe in set values, cuts unnecessary operations down bellow */
                        List<AppsProperty> properties2 = getPropertyList(entry2);
                        int                sizeP2      = properties2.size();

                        /* Compares sizes of both property lists*/
                        if (sizeP1 == sizeP2) {

                            /* Returns intersection of two lists */
                            Set<AppsProperty> result = properties1.stream()
                                    .distinct()
                                    .filter(properties2::contains)
                                    .collect(Collectors.toSet());

                            /* Checks size of list vs intersection */
                            if (sizeP1 == result.size()) {
                                /* Writes down a dupe match */
                                dupeEntrySet.add(entry2);
                                allDupesSet.add(entry2);

                            }
                        }
                    }

                }
            }
        }
        return duplicateMap;
    }

    /** Merges duplicate groups into corresponding entries */
    protected List<FilterEntry> mergeDuplicateEntries(Map<FilterEntry, Set<FilterEntry>> dupeEntryMap) {
        List<FilterEntry> entries = new ArrayList<>();
        dupeEntryMap.forEach((entry, dupeSet) -> {

            /* Merge text from fields */
            Set<String> fromSet = new HashSet<>(getUniqueStrings(entry, XmlConst.FROM));
            dupeSet.forEach(entry1 -> fromSet.addAll(getUniqueStrings(entry1, XmlConst.FROM)));
            String from = String.join(TextConst.GMAIL_OR, fromSet);

            /* Update property of entry */
            List<AppsProperty> properties = entry.getProperties();
            properties.stream()
                    .filter(appsProperty -> appsProperty.getName().equals(XmlConst.FROM))
                    .forEach(appsProperty -> appsProperty.setValue(from));

            /* Add merged data into single entry */
            entry.setProperties(properties);

            /* Adds entry into returned list */
            entries.add(entry);
        });
        return entries;
    }

    /** @return Set<' String>, Set of strings. */
    private Set<String> getUniqueStrings(FilterEntry resultEntry, String propertyName) {
        Set<String> strings = new HashSet<>();
        for (AppsProperty p : resultEntry.getProperties()) {
            if (p.getName().equals(propertyName)) {
                strings.addAll(List.of(splitAndTrimString(p.getValue())));
                break;
            }
        }
        return strings;
    }

    /**
     * Splits and trims string. Uses delimiter regex "[| OR]+" .
     * @return Array of strings
     */
    protected String[] splitAndTrimString(String from) {
        String[] strings = from.split("[| OR]+");
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].trim();
        }
        return strings;
    }

}
