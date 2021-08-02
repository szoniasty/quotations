package com.dbojanek.quotations.model;

public class Quote {
    private final Long id;
    private String quote;
    private final Author author;

    public Quote(Long id, String quote, Author author) {
        this.id = id;
        this.quote = quote;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public String getQuote() {
        return quote;
    }

    public Author getAuthor() {
        return author;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}
