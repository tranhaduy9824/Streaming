package org.example.client.backend;

import java.util.List;
import java.util.ArrayList;

public class Backend {
    public static List<String> getVideoList() {
        List<String> videoList = new ArrayList<>();
        videoList.add("1|Alice|10|titleStream");
        videoList.add("Room2|Bob|20|titleStream");
        videoList.add("Room3|Charlie|30|titleStream");
        
        videoList.add("Room1|Alice|10|titleStream");
        videoList.add("Room2|Bob|20|titleStream");
        videoList.add("Room3|Charlie|30|titleStream");
        
        videoList.add("Room1|Alice|10|titleStream");
        videoList.add("Room2|Bob|20|titleStream");
        videoList.add("Room3|Charlie|30|titleStream");
        
        videoList.add("Room1|Alice|10|titleStream");
        videoList.add("Room2|Bob|20|titleStream");
        videoList.add("Room3|Charlie|30|titleStream");
        
        return videoList;
    }
}
