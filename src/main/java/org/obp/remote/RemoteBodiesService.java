/*
 * Copyright 2013-2014 MARSEC-XL International Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.obp.remote;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import org.obp.data.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Robert Jaremczak
 * Date: 2014-4-18
 */

@Service
public class RemoteBodiesService {
    @Autowired
    private CacheManager cacheManager;

    private Ehcache cache;

    @PostConstruct
    public void init() {
        cache = (Ehcache)cacheManager.getCache("remoteBodies").getNativeCache();
    }

    public Collection<Body> getAll() {
        Collection<Body> bodies = new Stack<>();
        for(Map.Entry<Object,Element> entry : cache.getAll(cache.getKeysWithExpiryCheck()).entrySet()) {
            bodies.add((Body)entry.getValue().getObjectValue());
        }
        return bodies;
    }

    public void clear() {
        cache.removeAll();
    }

    public void put(Body body) {
        cache.put(new Element(body.getId(), body));
    }
}
