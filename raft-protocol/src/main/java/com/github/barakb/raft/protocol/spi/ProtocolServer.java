package com.github.barakb.raft.protocol.spi;

import java.util.concurrent.CompletionStage;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public interface ProtocolServer {

    CompletionStage<Void> listen();
    CompletionStage<Void> close();
    ProtocolServer handler(ProtocolHandler handler);
}
