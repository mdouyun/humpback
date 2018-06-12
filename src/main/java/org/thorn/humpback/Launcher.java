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

package org.thorn.humpback;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thorn.common.SpringContext;
import org.thorn.humpback.frame.service.WinRegistry;
import org.thorn.humpback.frame.view.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Launcher {

    static Logger log = LoggerFactory.getLogger(Launcher.class);

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    SpringContext.loadApplicationContext("humpback-Spring.xml");

                    WinRegistry registry = SpringContext.getBean(WinRegistry.class);

                    LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
                    loggerContext.reset();
                    loggerContext.putProperty("location", registry.getLocation() + "/log");
                    JoranConfigurator joranConfigurator = new JoranConfigurator();
                    joranConfigurator.setContext(loggerContext);

                    URL url = Launcher.class.getResource("/log.xml");
                    joranConfigurator.doConfigure(url);

                    JFrame frame = new MainFrame();
                    frame.setVisible(true);
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                } catch (Exception e) {
                    log.error("Launcher exception", e);
                }
            }
        });
    }

}
