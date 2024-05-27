package com.museum.backend.security.models;


import java.util.List;

public class AuthorizationRules {
    List<Rule> rules;

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }
}
