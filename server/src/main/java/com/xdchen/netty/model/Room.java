package com.xdchen.netty.model;

import com.xdchen.netty.exception.BusiException;
import com.xdchen.netty.model.db.User;
import com.xdchen.netty.service.CardService;
import com.xdchen.netty.service.ICardService;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Room {
    private static final int MAX_USERS = 3;
    private Map<Channel, User> chanelToUserMap = new ConcurrentHashMap<>(3);
    private Map<Channel, List<Card>> userCards = new ConcurrentHashMap<>(3);
    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private Channel master = null;
    private volatile boolean canStart = false,
            start = false;

    public synchronized void addUser(Channel channel, User user) {
        if (channels.size() >= MAX_USERS) {
            throw new BusiException("房间已经满人");
        }
//        if (start) {
//            throw new BusiException("游戏已经开始");
//        }
        if (channels.size() == 0) {
            master = channel;
        }
        chanelToUserMap.put(channel, user);
        channels.add(channel);
        if (chanelToUserMap.size() == MAX_USERS) {
            this.start();
        }
    }

    public synchronized void removeUser(Channel channel) {
        boolean temp = canStart;
        canStart = false;
        chanelToUserMap.remove(channel);
        this.setMaster();
        if (temp && master != null) {
            master.writeAndFlush(new TextWebSocketFrame("{\"code\": 0, \"cmd\":3}"));
        }
    }

    private void start() {
//        if (start) {
//            throw new BusiException("游戏已经开始");
//        }
        if (channels.size() != MAX_USERS) {
            throw new BusiException("人数不够");
        }
        canStart = true;
        userCards.clear();
        for (Channel channel : channels) {
            userCards.put(channel, new LinkedList<>());
        }
        master.writeAndFlush(new TextWebSocketFrame("{\"code\": 0, \"cmd\":2}"));
    }

    public void setMaster() {
        if (!channels.contains(master)) {
            Iterator<Channel> iterator = channels.iterator();
            if (iterator.hasNext()) {
                master = iterator.next();
            } else {
                master = null;
            }
        }
    }

    public Map<Channel, User> getChanelToUserMap() {
        return chanelToUserMap;
    }

    public void setChanelToUserMap(Map<Channel, User> chanelToUserMap) {
        this.chanelToUserMap = chanelToUserMap;
    }

    public Map<Channel, List<Card>> getUserCards() {
        return userCards;
    }

    public void setUserCards(Map<Channel, List<Card>> userCards) {
        this.userCards = userCards;
    }

    public ChannelGroup getChannels() {
        return channels;
    }

    public void setChannels(ChannelGroup channels) {
        this.channels = channels;
    }

    public Channel getMaster() {
        return master;
    }

    public void setMaster(Channel master) {
        this.master = master;
    }
}
