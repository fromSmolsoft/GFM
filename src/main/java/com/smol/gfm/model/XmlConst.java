package com.smol.gfm.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/** Constants for elements used in exported gmail filters *.xml */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlConst {
    //header
    public static final String FEED          = "feed";
    public static final String AUTHOR        = "author";
    public static final String NAME          = "name";
    public static final String EMAIL         = "email";
    //entry element
    public static final String ENTRY         = "entry";
    public static final String CATEGORY      = "category";
    public static final String TERM          = "term";
    public static final String TITLE         = "title";
    public static final String ID            = "id";
    public static final String UPDATED       = "updated";
    public static final String CONTENT       = "content";
    public static final String APPS_PROPERTY = "apps:property";
    public static final String FROM          = "from";
    public static final String VALUE         = "value";

    public static final String ID_TAG_HEADER = "tag:mail.google.com,2008:filters:";
    public static final String ID_TAG_ENTRY  = "tag:mail.google.com,2008:filter:";


    public static final String PATH_MAIL_FILTERS_XML = "C:\\Users\\Martin Smolek\\Development\\IdeaProjects\\Apps\\GmailFilterManager\\Gmail Filters\\mailFilters.xml";
    public static final String PATH_EX_MAIL_FILTERS_XML = "C:\\Users\\Martin Smolek\\Development\\IdeaProjects\\Apps\\GmailFilterManager\\Gmail Filters\\mailFilters_new.xml";
}
