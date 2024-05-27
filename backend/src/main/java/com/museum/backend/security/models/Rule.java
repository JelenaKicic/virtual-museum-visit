package com.museum.backend.security.models;

import java.util.List;

public class Rule {
    private List<String> methods;
    private String pattern;
    private List<String> roles;

    public Rule() {
    }

    public Rule(List<String> methods, String pattern, List<String> roles) {
        this.methods = methods;
        this.pattern = pattern;
        this.roles = roles;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}