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

package org.obp;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by Robert Jaremczak
 * Date: 2013-10-24
 */

@Component
public class Introspector extends BaseExplorer {
    private static Logger logger = Logger.getLogger(Introspector.class);

    private List<Body> bodies;

    public Introspector() {
        super(UUID.randomUUID(), "introspector", "discovers local bodies");
    }

    @PostConstruct
    protected void init() {
        bodies = Collections.unmodifiableList(Arrays.asList(new Body("own")));
    }

    @Override
    public List<Body> scan() {
        return bodies;
    }
}
