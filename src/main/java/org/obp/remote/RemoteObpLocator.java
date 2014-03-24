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

import net.maritimecloud.net.broadcast.BroadcastListener;
import net.maritimecloud.net.broadcast.BroadcastMessageHeader;
import org.apache.log4j.Logger;
import org.obp.ObpInstance;
import org.obp.maritimecloud.ObpBeaconMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-17
 */

@Service
public class RemoteObpLocator implements BroadcastListener<ObpBeaconMessage> {

    private Logger logger = Logger.getLogger(RemoteObpLocator.class);

    private ConcurrentMap<UUID,RemoteObpInstance> remoteInstances;
    private List<ObpInstance> remotes;

    @Autowired
    public RemoteObpLocator(@Value("#{'${obp.remote.uris}'.split(',')}") URI[] remoteUris) {
        List<ObpInstance> instances =  new ArrayList<>(remoteUris.length);
        for(URI uri : remoteUris) {
            instances.add(new RemoteObpInstance(uri));
        }
        remotes = new CopyOnWriteArrayList<>(instances);
        remoteInstances = new ConcurrentHashMap<>();
    }

    private void updateRemote(RemoteObpInstance remoteObpInstance) {

    }

    private int calculateBeingInformedRank(ObpInstance obpInstance) {
        return obpInstance.knownRemotes() + (obpInstance.isHub() ? 1000000 : 0);
    }

    public ObpInstance bestInformedRemote() {
        int bestRank = 0;
        ObpInstance bestInstance = null;

        for(ObpInstance instance : remotes) {
            int rank = calculateBeingInformedRank(instance);
            if(rank > bestRank) {
                bestInstance = instance;
                bestRank = rank;
            }
        }

        return bestInstance;
    }

    public int knownRemotes() {
        return remotes.size();
    }

    @Override
    public void onMessage(BroadcastMessageHeader header, ObpBeaconMessage broadcast) {
        logger.info("OBP beacon message received: "+broadcast);
    }
}
