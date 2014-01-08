package org.obp.web.config;

import org.obp.utils.MapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-16
 */
@Service
public class ObpConfig {

    @Value("${build.id}")
    private String buildId;

    @Value("${google.api.key}")
    private String googleApiKey;

    @Value("${obp.local.hub}")
    private boolean hub;

    @Value("${obp.local.uri}")
    private URI uri;

    public boolean isHub() {
        return hub;
    }

    public String getBuildId() {
        return buildId;
    }

    public String getGoogleApiKey() {
        return googleApiKey;
    }

    public Object getJavaInfo() {
        return MapUtil.filterByKeyPrefix(System.getProperties(),"java.");
    }

    public Object getOsInfo() {
        return MapUtil.filterByKeyPrefix(System.getProperties(),"os.");
    }

    public Map<String,Object> getComponents() {
        Map<String,Object> map = new HashMap<>();
        return map;
    }

    public URI getUri() {
        return uri;
    }

}
