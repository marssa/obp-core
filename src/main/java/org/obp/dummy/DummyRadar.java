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

package org.obp.dummy;

import org.apache.log4j.Logger;
import org.obp.BaseExplorer;
import org.obp.data.Body;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-27
 */

public class DummyRadar extends BaseExplorer {
    private static Logger logger = Logger.getLogger(DummyRadar.class);

    private List<Body> bodies;

    public DummyRadar() {
        super(UUID.randomUUID().toString(), "dummyRadar", "dummy radar as external bodies explorer");
    }

    @PostConstruct
    protected void init() {
        bodies = Collections.unmodifiableList(Arrays.asList(
                new Body("Pinta",1,1),
                new Body("Nina",2,2),
                new Body("Santa Maria",3,3)
        ));
    }

    @Override
    public List<Body> scan() {
        return bodies;
    }
}
