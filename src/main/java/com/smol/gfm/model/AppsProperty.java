package com.smol.gfm.model;

import lombok.Data;

@Data
public class AppsProperty {
    private String name;
    private String value;

    @Override
    public String toString() {
        return "\n\t\t<apps:property " +
               "name='" + name + '\'' +
               " value='" + value + '\'' +
               "/>";
    }
}
