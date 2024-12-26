package org.example.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constants {
    public static final String SERVER_ADDRESS;
    public static final int SERVER_PORT;
    public static final int BROADCAST_PORT;

    static {
        String address = "127.0.0.1";
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        SERVER_ADDRESS = "192.168.1.20"; //ip server || address
        SERVER_PORT = 8080;
        BROADCAST_PORT = 9877;


    }
}