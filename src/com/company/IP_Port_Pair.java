package com.company;

public class IP_Port_Pair {
    protected String ip;
    protected String  port;
    protected int index;


    public IP_Port_Pair(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public void setIndex(int idx) {
        this.index = idx;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj)
            return true;
        if (obj instanceof IP_Port_Pair pair) {

            return pair.ip.equals(this.ip) && pair.port.equals(this.port);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return ip.hashCode() * port.hashCode();
    }

}
