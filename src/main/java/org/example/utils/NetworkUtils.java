package org.example.utils;

import java.net.DatagramSocket;

public class NetworkUtils {
    public static DatagramSocket createSocket(int port) throws Exception {
        return new DatagramSocket(port);
    }
}
