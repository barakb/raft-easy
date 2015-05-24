package com.github.barakb.raft.protocol;

import java.net.URI;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public interface ProtocolBuilder {
    ProtocolClient buildClient(URI uri);
    ProtocolServer buildServer(URI uri);
}
