package org.obp.remote;

import org.apache.log4j.Logger;
import org.obp.ObpInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Robert Jaremczak
 * Date: 2013-12-17
 */

@Service
public class RemoteObpLocator {

    private Logger logger = Logger.getLogger(RemoteObpLocator.class);

    private List<ObpInstance> remotes;

    @Autowired
    public RemoteObpLocator(@Value("#{'${obp.remote.uris}'.split(',')}") URI[] remoteUris) {
        List<ObpInstance> instances =  new ArrayList<>(remoteUris.length);
        for(URI uri : remoteUris) {
            instances.add(new RemoteObpInstance());
        }
        remotes = new CopyOnWriteArrayList<>(instances);
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

}
