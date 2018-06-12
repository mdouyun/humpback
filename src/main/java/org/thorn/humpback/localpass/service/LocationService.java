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

package org.thorn.humpback.localpass.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thorn.humpback.frame.service.WinRegistry;

/**
 * 注册表读写密码本地址操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
@Service
public class LocationService {

    private static final int MAX_NOTES_NUMBER = 5;

    private final static String RECENT_NOTES = "recentNotes";

    @Autowired
    private WinRegistry registry;

    public String[] getRecentNotes() {
        String paths = registry.get(RECENT_NOTES);
        return StringUtils.split(paths, ";");
    }

    public void addOpenedNote(String notePath) {
        String paths = registry.get(RECENT_NOTES);
        String[] notes = StringUtils.split(paths, ";");

        String[] newNotes = new String[MAX_NOTES_NUMBER];

        newNotes[0] = notePath;
        int i = 1;
        for (String note : notes) {

            if (i >= MAX_NOTES_NUMBER) {
                break;
            }

            if (!StringUtils.equals(note, notePath)) {
                newNotes[i] = note;
                i++;
            }
        }

        registry.put(RECENT_NOTES, StringUtils.join(newNotes, ";"));
    }

    public void setNotesSaveFolder(String folder) {
        registry.setLocation(folder);
    }

    public String getNotesSaveFolder() {
        return registry.getLocation();
    }

}
