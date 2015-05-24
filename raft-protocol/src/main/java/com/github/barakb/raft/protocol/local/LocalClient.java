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
public class LocalClient implements ProtocolClient {

    private static final Logger logger = LoggerFactory.getLogger(ProtocolClient.class);

    private final Map<String, LocalServer> registry;
    private final String id;

    @Inject
    public LocalClient(@LocalRegistry Map<String, LocalServer> registry, @Assisted String id) {
        this.registry = registry;
        this.id = id;
        logger.debug("client {} created", id);
    }

    @Override
    public CompletionStage<Void> connect() {
        logger.debug("client {} connected", id);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<Void> close() {
        logger.debug("client {} closed", id);
        return CompletableFuture.completedFuture(null);
    }

    @Override
    public CompletionStage<VoteResponse> sendVoteRequest(VoteRequest request) {
        return registry.get(id).sendVoteRequest(request);
    }

    @Override
    public CompletionStage<AppendEntriesResponse> sendAppendEntries(AppendEntriesRequest request) {
        return registry.get(id).sendAppendEntries(request);
    }

    @Override
    public String toString() {
        return "LocalClient{" +
                "id='" + id + '\'' +
                '}';
    }
}
