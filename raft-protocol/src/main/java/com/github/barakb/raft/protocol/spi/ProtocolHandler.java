package com.github.barakb.raft.protocol.spi;

import java.util.concurrent.CompletionStage;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public interface ProtocolHandler {
    CompletionStage<VoteResponse> sendVoteRequest(VoteRequest request);
    CompletionStage<AppendEntriesResponse> sendAppendEntries(AppendEntriesRequest request);

}
