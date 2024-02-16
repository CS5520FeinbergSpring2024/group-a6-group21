package edu.northeastern.group21;

public class SearchResult {

    private int page;
    private String next;
    private int entries;

    private MovieJson results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public MovieJson getResults() {
        return results;
    }

    public void setResults(MovieJson results) {
        this.results = results;
    }
}
