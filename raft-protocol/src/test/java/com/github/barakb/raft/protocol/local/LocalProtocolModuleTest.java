package com.github.barakb.raft.protocol.local;

import com.github.barakb.raft.protocol.spi.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

import static org.easymock.EasyMock.*;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public class LocalProtocolModuleTest {
    @SuppressWarnings("unused")
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
        ProtocolHandler serverHandler = createNiceMock(ProtocolHandler.class);
        VoteRequest voteRequest = new VoteRequest();
        AppendEntriesRequest appendEntriesRequest = new AppendEntriesRequest();

        fooServer.handler(serverHandler);
        expect(serverHandler.sendVoteRequest(voteRequest)).andReturn(CompletableFuture.completedFuture(new VoteResponse()));
        expect(serverHandler.sendAppendEntries(appendEntriesRequest)).andReturn(CompletableFuture.completedFuture(new AppendEntriesResponse()));
        replay(serverHandler);
        fooServer.listen().toCompletableFuture().get();
        fooClient.connect().toCompletableFuture().get();
        try {
            fooClient.sendVoteRequest(voteRequest).toCompletableFuture().get();
            fooClient.sendAppendEntries(appendEntriesRequest).toCompletableFuture().get();
        } finally {
            fooClient.close().toCompletableFuture().get();
            fooServer.close().toCompletableFuture().get();
        }
        verify(serverHandler);

    }
}