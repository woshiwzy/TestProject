package com.wangzy.flight;

import java.util.ArrayList;

public class FlightTemplate {

    private String template;
    private ArrayList<String> child;
    private String configFile;

    public FlightTemplate() {
        child = new ArrayList<>();
    }

    public boolean existConfig() {
        return (null == configFile || configFile.isEmpty())? false : true;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }


    public ArrayList<String> getChild() {
        return child;
    }

    public void setChild(ArrayList<String> child) {
        this.child = child;
    }

    public String getConfigFile() {
        return configFile;
    }

    public void setConfigFile(String configFile) {
        this.configFile = configFile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FlightTemplate that = (FlightTemplate) o;

        return template.equals(that.template);
    }

    @Override
    public int hashCode() {
        return template.hashCode();
    }

    public void addChild(String child) {
        this.child.add(child);
    }
}
