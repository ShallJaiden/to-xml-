package com.company;

public class Zone {
    protected String name;
    protected int serverIndex;
    protected int capacity;



    public Zone(String name, int capacity, int serverIndex) {
        this.name = name;
        this.capacity = capacity;
        this.serverIndex = serverIndex;

    }
}
