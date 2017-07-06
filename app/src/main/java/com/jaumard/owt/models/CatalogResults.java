package com.jaumard.owt.models;

import java.util.ArrayList;
import java.util.List;

public class CatalogResults {
    private List<CatalogEntry> results = new ArrayList<>();

    public CatalogResults(List<CatalogEntry> results) {
        this.results = results;
    }

    public List<CatalogEntry> getResults() {
        return results;
    }
}
