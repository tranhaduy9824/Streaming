package org.example.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constants {
    public static final String SERVER_ADDRESS;
    public static final int SERVER_PORT = 8080;
    public static final int BROADCAST_PORT = 9877;

    static {
        String address = "127.0.0.1";
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        SERVER_ADDRESS = address;
    }
}