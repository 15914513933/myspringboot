package com.chenkj.myspringboot.zookeeper;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

/**
 * @Author
 * @Description
 * @Date 2020-08-17 16:47
 */

public class ZkClient implements Watcher {
    private ZooKeeper zooKeeper;

    public ZooKeeper connect(String host, int port, int timeout) throws IOException {
        return new ZooKeeper(host + ":" + port, 5000, this);
    }

    @Override
    public void process(WatchedEvent watchedEvent) {

    }

    public static void main(String[] args) throws IOException {
        new ZkClient().connect("47.113.122.122",2181,5000);
    }
}
