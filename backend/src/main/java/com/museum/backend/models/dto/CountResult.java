package com.museum.backend.models.dto;

import java.util.Objects;

public class CountResult {
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CountResult)) return false;
        CountResult that = (CountResult) o;
        return count.equals(that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    private Long count;

    public CountResult(Long count) {
        this.count = count;
    }
}
