package com.dbojanek.quotations.model.dto;

import java.io.Serializable;
import java.util.Objects;

public class QuoteDTO implements Serializable {
    private long id;
    private String quote;
    private String authorName;
    private String authorSurname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorSurname() {
        return authorSurname;
    }

    public void setAuthorSurname(String authorSurname) {
        this.authorSurname = authorSurname;
    }

    @Override
    public String toString() {
        return "QuoteDTO{" +
                "id=" + id +
                ", quote='" + quote + '\'' +
                ", authorName='" + authorName + '\'' +
                ", authorSurname='" + authorSurname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuoteDTO quoteDTO = (QuoteDTO) o;
        return id == quoteDTO.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
