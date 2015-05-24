package com.github.barakb.raft.protocol.local;

import com.github.barakb.di.Factory;
import com.github.barakb.raft.protocol.ProtocolClient;
import com.github.barakb.raft.protocol.ProtocolServer;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public class LocalProtocolModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new FactoryModuleBuilder()
                .implement(ProtocolClient.class, LocalClient.class)
                .build(new TypeLiteral<Factory<String, LocalClient>>() {
                }));
        install(new FactoryModuleBuilder()
                .implement(ProtocolServer.class, LocalServer.class)
                .build(new TypeLiteral<Factory<String, LocalServer>>() {
                }));
        bind(LocalProtocolBuilder.class).asEagerSingleton();
        bind(new TypeLiteral<Map<String, LocalServer>>() {
        }).annotatedWith(LocalRegistry.class).toInstance(new HashMap<>());

    }

}
