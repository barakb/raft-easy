package com.github.barakb.raft.protocol;

import java.util.concurrent.CompletionStage;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public interface ProtocolClient extends ProtocolHandler {
    CompletionStage<Void> connect();
    CompletionStage<Void> close();

}
