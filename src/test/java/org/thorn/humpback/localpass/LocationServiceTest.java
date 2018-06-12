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

package org.thorn.humpback.localpass;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.thorn.common.SpringContext;
import org.thorn.humpback.localpass.service.LocationService;

/**
 *
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class LocationServiceTest extends TestCase {

    private LocationService locationService;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        SpringContext.loadApplicationContext("localPass.xml");

        locationService = SpringContext.getBean(LocationService.class);
    }

    public void testGetRecentNotes() throws Exception {
        String[] notes = locationService.getRecentNotes();
        Assert.assertTrue(notes.length >= 0);
        System.out.println(StringUtils.join(notes, ";"));
    }

    public void testAddOpenedNote() throws Exception {
        locationService.addOpenedNote("d:\\a.xtx");
        locationService.addOpenedNote("d:\\a2.xtx");
        locationService.addOpenedNote("d:\\a3.xtx");
        locationService.addOpenedNote("d:\\a4.xtx");
        locationService.addOpenedNote("d:\\a5.xtx");
        locationService.addOpenedNote("d:\\a6.xtx");
        locationService.addOpenedNote("d:\\a3.xtx");

        String[] notes = locationService.getRecentNotes();
        Assert.assertTrue(notes.length == 5);
        System.out.println(StringUtils.join(notes, ";"));

    }
}
