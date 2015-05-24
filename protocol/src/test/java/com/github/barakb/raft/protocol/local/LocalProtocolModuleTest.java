package com.github.barakb.raft.protocol.local;

import com.github.barakb.raft.protocol.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public class LocalProtocolModuleTest {
    private static final Logger logger = LoggerFactory.getLogger(LocalProtocolModuleTest.class);

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testConfigure() throws Exception {
        Injector injector = Guice.createInjector(new LocalProtocolModule());
        LocalProtocolBuilder lpb = injector.getInstance(LocalProtocolBuilder.class);
        ProtocolClient fooClient = lpb.buildClient(new URI("localhost://foo"));
        ProtocolServer fooServer = lpb.buildServer(new URI("localhost://foo"));
        fooServer.handler(new ProtocolHandler() {
            @Override
            public CompletionStage<VoteResponse> sendVoteRequest(VoteRequest request) {
                logger.debug("fooServer sendVoteRequest called");
                return CompletableFuture.completedFuture(null);
            }

            @Override
            public CompletionStage<AppendEntriesResponse> sendAppendEntries(AppendEntriesRequests request) {
                logger.debug("fooServer sendAppendEntries called");
                return CompletableFuture.completedFuture(null);
            }
        });
        fooServer.listen().toCompletableFuture().get();
        fooClient.connect().toCompletableFuture().get();
        try {
            fooClient.sendVoteRequest(new VoteRequest()).toCompletableFuture().get();
            fooClient.sendAppendEntries(new AppendEntriesRequests()).toCompletableFuture().get();
        } finally {
            fooClient.close().toCompletableFuture().get();
            fooServer.close().toCompletableFuture().get();
        }


    }
}