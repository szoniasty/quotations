package com.dbojanek.quotations.model;

public class Quote {
    private String quote;
    private Author author;

    public Quote(String quote, Author author) {
        this.quote = quote;
        this.author = author;
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

    public void setAuthor(Author author) {
        this.author = author;
    }
}
