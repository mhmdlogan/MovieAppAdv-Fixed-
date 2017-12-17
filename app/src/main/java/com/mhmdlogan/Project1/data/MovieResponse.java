package com.mhmdlogan.Project1.data;

import java.util.List;


 //Records the response from the movies DBAPI

public class MovieResponse {
    private Number page;
    private List<Movie> results;
    private Number total_pages;
    private Number total_results;

    public Number getPage() {
        return this.page;
    }

    public void setPage(Number page) {
        this.page = page;
    }

    public List<Movie> getResults() {
        return this.results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }

    public Number getTotal_pages() {
        return this.total_pages;
    }

    public void setTotal_pages(Number total_pages) {
        this.total_pages = total_pages;
    }

    public Number getTotal_results() {
        return this.total_results;
    }

    public void setTotal_results(Number total_results) {
        this.total_results = total_results;
    }
}