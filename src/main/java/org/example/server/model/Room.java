package org.example.server.model;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id;
    private String roomName;
    private int ownerId;
    private List<Participant> participants;
    private Timestamp startTime;
    private Timestamp endTime;
    private String multicastAddress;
    private int multicastPort;

    public Room() {
        this.participants = new ArrayList<>();
    }

    public Room(String roomName, int ownerId, String multicastAddress, int multicastPort) {
        this.roomName = roomName;
        this.ownerId = ownerId;
        this.participants = new ArrayList<>();
        this.startTime = new Timestamp(System.currentTimeMillis());
        this.multicastAddress = multicastAddress;
        this.multicastPort = multicastPort;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public int getParticipantCount() {
        return participants.size();
    }

    public void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public void removeParticipant(int userId) {
        participants.removeIf(participant -> participant.getUserId() == userId);
    }

    public boolean hasParticipant(int userId) {
        return participants.stream().anyMatch(participant -> participant.getUserId() == userId);
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getMulticastAddress() {
        return multicastAddress;
    }

    public void setMulticastAddress(String multicastAddress) {
        this.multicastAddress = multicastAddress;
    }

    public int getMulticastPort() {
        return multicastPort;
    }

    public void setMulticastPort(int multicastPort) {
        this.multicastPort = multicastPort;
    }
}