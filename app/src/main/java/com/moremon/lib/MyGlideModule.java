package com.moremon.lib;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.GlideModule;

/**
 * Glide 디스크 케쉬 사용 설정.
 * Created by Administrator on 2017-04-26.
 */
public class MyGlideModule implements GlideModule {
    private final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
    private final int cacheSize = maxMemory / 8;
    private final int DISK_CACHE_SIZE = 1024 * 1024 * 10;

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context, "cache", DISK_CACHE_SIZE))
                .setMemoryCache(new LruResourceCache(cacheSize));
    }

    @Override
    public void registerComponents(Context context, Glide glide) {

    }
}