package com.smol.gfm.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TextConst {
    public static final String ERROR              = "Something went wrong";
    public static final String GMAIL_OR           = " OR ";
    public static final String GMAIL_ALL_OR_REGEX = " ";
    public static final String ABOUT              = "About";
    public static final String ABOUT_DETAILS      = "It's work in progress = Use at your own risk\n" +
                                                    "\n\nReleased under: " +
                                                    "\nGNU LESSER GENERAL PUBLIC LICENSE\n " +
                                                    "Version 2.1 ";
}
