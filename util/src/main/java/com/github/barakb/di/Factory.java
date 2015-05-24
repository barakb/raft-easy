package com.github.barakb.di;

/**
 * Created by Barak Bar Orion
 * 5/24/15.
 */
public interface Factory<P, R> {
    R create(P param);
}
