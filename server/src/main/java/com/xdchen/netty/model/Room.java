package com.xdchen.netty.model;

import com.alibaba.fastjson.JSONArray;
import com.xdchen.netty.exception.BusiException;
import com.xdchen.netty.model.db.User;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private static final int MAX_USERS = 3;
    private Map<Channel, User> chanelToUserMap = new ConcurrentHashMap<>(3);
    private Map<String, List<Card>> userCards = new ConcurrentHashMap<>(3);
    private Vector<Channel> channelList = new Vector<>(3);
    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private Channel[] beginChannels = new Channel[3];
    private User[] beginUsers = new User[3];
    private Channel master = null;
    private volatile boolean canStart = false,
            start = false;
    private volatile int currentIndex = 0,
            cardsOwnerIndex = 0;
    private JSONArray currentCards = null;

    public synchronized void addUser(Channel channel, User user) {
        if (channels.size() >= MAX_USERS) {
            throw new BusiException("房间已经满人");
        }
        for (Map.Entry<Channel, User> entry : chanelToUserMap.entrySet()) {
            if (entry.getValue().getUsername().equals(user.getUsername())) {
                throw new BusiException("同个用户，不能重复登录");
            }
        }
        if (start) {
            boolean validUser = false;
            for (int i = 0; i < beginUsers.length; i++) {
                if (user.getUsername().equals(beginUsers[i].getUsername())) {
                    beginChannels[i] = channel;
                    channelList.set(i, channel);
                    validUser = true;
                    break;
                }
            }
            if (!validUser) {
                throw new BusiException("房间已经开始游戏");
            }
        } else {
            if (channels.size() == 0) {
                master = channel;
            }
            chanelToUserMap.put(channel, user);
            channels.add(channel);
            channelList.add(channel);
            if (chanelToUserMap.size() == MAX_USERS) {
                canStart = true;
                this.prepareStart();
            }
        }
    }

    public synchronized void removeUser(Channel channel) {
        if (start) {
            chanelToUserMap.remove(channel);
            int index = channelList.indexOf(channel);
            beginChannels[index] = null;
            channelList.set(index, null);
        } else {
            boolean temp = canStart;
            canStart = false;
            chanelToUserMap.remove(channel);
            channelList.remove(channel);
            this.setMaster();
            if (temp && master != null) {
                master.writeAndFlush(new TextWebSocketFrame("{\"code\": 0, \"cmd\":3}"));
            }
        }
    }

    public String getUsernameByChannel(Channel channel) {
        return beginUsers[channelList.indexOf(channel)].getUsername();
    }

    private void prepareStart() {
        if (start) {
            throw new BusiException("游戏已经开始");
        }
        if (channels.size() != MAX_USERS) {
            throw new BusiException("人数不够");
        }
        canStart = true;
        userCards.clear();
        int i = 0;
        for (Channel channel : channelList) {
            beginChannels[i] = channel;
            beginUsers[i] = chanelToUserMap.get(channel);
            userCards.put(beginUsers[i].getUsername(), new LinkedList<>());
            i++;
        }
        master.writeAndFlush(new TextWebSocketFrame("{\"code\": 0, \"cmd\":2}"));
    }

    public void startGame() {
        start = true;
        currentIndex = channelList.indexOf(master);
    }

    public int nextTurn() {
        currentIndex = (currentIndex + 1) % MAX_USERS;
        return currentIndex;
    }

    public void setCurrentCards(Channel channel, JSONArray cards) {
        this.currentCards = cards;
        this.cardsOwnerIndex = channelList.indexOf(channel);
    }

    public synchronized void gameOver() {
        canStart = chanelToUserMap.size() >= MAX_USERS;
        start = false;
        channelList.clear();
        for (Channel channel : beginChannels) {
            if (channel != null) {
                channelList.add(channel);
            }
        }
        this.setMaster();
    }

    private void setMaster() {
        if (!channels.contains(master)) {
            master = null;
            for (Channel channel : channelList) {
                if (channel != null) {
                    master = channel;
                    break;
                }
            }
        }
    }

    public Map<Channel, User> getChanelToUserMap() {
        return chanelToUserMap;
    }

    public void setChanelToUserMap(Map<Channel, User> chanelToUserMap) {
        this.chanelToUserMap = chanelToUserMap;
    }

    public Map<String, List<Card>> getUserCards() {
        return userCards;
    }

    public void setUserCards(Map<String, List<Card>> userCards) {
        this.userCards = userCards;
    }

    public Vector<Channel> getChannelList() {
        return channelList;
    }

    public void setChannelList(Vector<Channel> channelList) {
        this.channelList = channelList;
    }

    public ChannelGroup getChannels() {
        return channels;
    }

    public void setChannels(ChannelGroup channels) {
        this.channels = channels;
    }

    public Channel[] getBeginChannels() {
        return beginChannels;
    }

    public void setBeginChannels(Channel[] beginChannels) {
        this.beginChannels = beginChannels;
    }

    public User[] getBeginUsers() {
        return beginUsers;
    }

    public void setBeginUsers(User[] beginUsers) {
        this.beginUsers = beginUsers;
    }

    public Channel getMaster() {
        return master;
    }

    public void setMaster(Channel master) {
        this.master = master;
    }

    public boolean isCanStart() {
        return canStart;
    }

    public void setCanStart(boolean canStart) {
        this.canStart = canStart;
    }

    public boolean isStart() {
        return start;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getCardsOwnerIndex() {
        return cardsOwnerIndex;
    }

    public JSONArray getCurrentCards() {
        return currentCards;
    }
}
