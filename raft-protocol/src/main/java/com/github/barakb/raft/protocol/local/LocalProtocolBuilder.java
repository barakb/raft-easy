package com.github.barakb.raft.protocol.local;

import com.github.barakb.di.Factory;
import com.github.barakb.raft.protocol.spi.ProtocolBuilder;
import com.github.barakb.raft.protocol.spi.ProtocolClient;
import com.github.barakb.raft.protocol.spi.ProtocolServer;
import com.google.inject.Inject;

import java.net.URI;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public class LocalProtocolBuilder implements ProtocolBuilder {
    private final Factory<String, LocalClient> clientFactory;
    private final Factory<String, LocalServer> serverFactory;

    @Inject
    public LocalProtocolBuilder(Factory<String, LocalClient> clientFactory, Factory<String, LocalServer> serverFactory) {
        this.clientFactory = clientFactory;
        this.serverFactory = serverFactory;
    }

    @Override
    public ProtocolClient buildClient(URI uri) {
        return clientFactory.create(uri.toString());
    }

    @Override
    public ProtocolServer buildServer(URI uri) {
        return serverFactory.create(uri.toString());
    }
}
