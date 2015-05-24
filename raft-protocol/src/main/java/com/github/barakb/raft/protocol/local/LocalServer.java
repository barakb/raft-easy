package com.github.barakb.raft.protocol.local;

import com.github.barakb.raft.protocol.spi.*;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public class LocalServer implements ProtocolServer, ProtocolHandler {
    private static final Logger logger = LoggerFactory.getLogger(LocalServer.class);

    private final Map<String, LocalServer> registry;
    private final String id;
    private ProtocolHandler handler;

    @Inject
    public LocalServer(@LocalRegistry Map<String, LocalServer> registry, @Assisted String id) {
        this.registry = registry;
        this.id = id;
        logger.debug("server {} created", id);    }

    @Override
    public CompletionStage<Void> listen() {
        this.registry.put(id, this);
        logger.debug("server {} listen", id);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<Void> close() {
        this.registry.remove(id, this);
        logger.debug("server {} closed", id);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public ProtocolServer handler(ProtocolHandler handler) {
        this.handler = handler;
        return this;
    }

    @Override
    public CompletionStage<VoteResponse> sendVoteRequest(VoteRequest request) {
        return this.handler.sendVoteRequest(request);
    }

    @Override
    public CompletionStage<AppendEntriesResponse> sendAppendEntries(AppendEntriesRequest request) {
        return this.handler.sendAppendEntries(request);
    }

    @Override
    public String toString() {
        return "LocalServer{" +
                "id='" + id + '\'' +
                '}';
    }
}
