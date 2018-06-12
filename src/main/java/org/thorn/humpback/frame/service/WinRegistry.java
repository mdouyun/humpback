/*
 * Copyright (c) 2014 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.thorn.humpback.frame.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.prefs.Preferences;

/**
 * window注册表操作类.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
@Service
public class WinRegistry {

    private final static String KEY_NODE = "org/thorn/humpback";

    private Preferences preferences = Preferences.userRoot().node(KEY_NODE);

    private final static String APP_DIR = "location";

    private final static String DEFAULT_APP_DIR = "./humpback";

    public void put(String key, String value) {
        preferences.put(key, value);
    }

    public String get(String key, String defaultValue) {
        return preferences.get(key, defaultValue);
    }

    public String get(String key) {
        return get(key, "");
    }

    public String getLocation() {
        String folder = get(APP_DIR, DEFAULT_APP_DIR);

        File file = new File(folder);
        if (!file.exists()) {
            file.mkdirs();
        }

        return folder;
    }

    public void setLocation(String path) {
        put(APP_DIR, path);
    }


}
