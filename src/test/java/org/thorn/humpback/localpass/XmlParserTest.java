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

import junit.framework.TestCase;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.thorn.humpback.localpass.entity.Account;
import org.thorn.humpback.localpass.service.XmlParser;

import java.util.List;

/**
 * 文件读与写操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
public class XmlParserTest extends TestCase {

    private XmlParser xmlParser;

    private String xml = "<Root>" +
            "<Account><id>1</id><address>www.iteye.com</address><username>cy</username><password>12123</password><remark></remark></Account>" +
            "<Account><id>2</id><address>www.iteye.com</address><username>cy</username><password>12123</password><remark></remark></Account>" +
            "</Root>";

    private Account node = new Account();

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        xmlParser = new XmlParser();
        node.setId("2");
        node.setAddress("www.it");
        node.setUsername("chenyun");
        node.setPassword("123");
        node.setRemark("aaaa");
    }

    public void testParse() throws Exception {
        List<Account> list = xmlParser.parse(xml);
        Assert.isTrue(list.size() == 2);
    }

    public void testModifyNode() throws Exception {
        String newXml = xmlParser.modifyNode(xml, node);

        System.out.println("testModifyNode:" + newXml);

        List<Account> list = xmlParser.parse(newXml);
        Assert.isTrue(list.get(1).getId().equals("2"));
    }

    public void testAddNode() throws Exception {
        String newXml = xmlParser.addNode(xml, node);

        System.out.println("testAddNode:" + newXml);

        List<Account> list = xmlParser.parse(newXml);
        Assert.isTrue(list.get(2).getId().equals("2"));
    }

    public void testDelNode() throws Exception {
        String newXml = xmlParser.delNode(xml, node);

        System.out.println("testDelNode:" + newXml);

        List<Account> list = xmlParser.parse(newXml);
        Assert.isTrue(list.size() == 1);
    }

    public void testConvert() throws Exception {
        List<Account> list = xmlParser.parse(xml);
        String newXml = xmlParser.convert(list);

        System.out.println("testConvert:" + newXml);

        List<Account> list2 = xmlParser.parse(newXml);
        Assert.isTrue(list2.size() == 2);
    }

    public void testSplit() {

        String a = "aaaa";

        String[] a1 = a.split("#");

        System.out.println(a1.length + "---" + a1[0]);

        String[] a2 = StringUtils.split(a, "#");
        System.out.println(a2.length + "---" + a2[0]);

    }
}
