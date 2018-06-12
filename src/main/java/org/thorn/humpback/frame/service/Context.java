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

import org.thorn.humpback.frame.view.MainFrame;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 应用内存上下文.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class Context {

    private final static Map<String, Object> CONTEXT_MAP = new HashMap<String, Object>();

    public static MainFrame MAIN_FRAME = null;

    private static final String SELECTED_TAGS = "SELECTED_TAGS";

    public static void clearSelectedTags() {
        Set<String> selectedTags = (Set<String>) CONTEXT_MAP.get(SELECTED_TAGS);
        if(selectedTags != null) {
            selectedTags.clear();
        }
    }

    public static void addSelectedTags(String tag) {
        Set<String> selectedTags = (Set<String>) CONTEXT_MAP.get(SELECTED_TAGS);
        if(selectedTags != null) {
            selectedTags.add(tag);
        } else {
            selectedTags = new HashSet<String>();
            selectedTags.add(tag);
            CONTEXT_MAP.put(SELECTED_TAGS, selectedTags);
        }
    }

    public static void removeSelectedTags(String tag) {
        Set<String> selectedTags = (Set<String>) CONTEXT_MAP.get(SELECTED_TAGS);
        if(selectedTags != null) {
            selectedTags.remove(tag);
        }
    }

    public static Set<String> getSelectedTags() {
        Set<String> selectedTags = (Set<String>) CONTEXT_MAP.get(SELECTED_TAGS);

        return selectedTags;
    }

    public static Object get(String key) {
        return CONTEXT_MAP.get(key);
    }

    public static void put(String key, Object value) {
        CONTEXT_MAP.put(key, value);
    }

}
