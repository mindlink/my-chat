package com.mindlinksoft.recruitment.mychat;

import java.util.Objects;

/*
POJO for a sender
 */

public class Sender {
    private String name;
    private int id;

    public Sender(String name){
        this.name = name;
        this.id = this.hashCode();
    }

    public String getName() {
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sender sender = (Sender) o;
        return Objects.equals(name, sender.name);
    }

    // Assuming that a users name is their identifier
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }


}
