package org.kaorun.financetracker.model;

public interface Identifiable<ID> {
    ID getId();

    void setId(ID id);
}
