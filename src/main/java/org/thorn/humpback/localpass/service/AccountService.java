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

import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thorn.common.security.AES;
import org.thorn.humpback.localpass.entity.Account;
import org.thorn.humpback.localpass.entity.Session;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 账户对外提供的操作.
 *
 * @author chenyun313@gmail.com, 2014-03-27.
 * @version 1.0
 * @since 1.0
 */
@Service
public class AccountService {

    @Autowired
    private FileStore fileStore;

    @Autowired
    private XmlParser xmlParser;

    private static Session session = null;

    @Autowired
    private LocationService locationService;

    private void autoBackUp(String fileName) {
        File file = new File(fileName);

        if(!file.exists()) {
            return ;
        }

        long lastModifiedTime = file.lastModified();
        long autoBackupTime = 1000 * 60 * 60 * 5;
        if (System.currentTimeMillis() - lastModifiedTime > autoBackupTime) {
            File backUpFolder = new File(locationService.getNotesSaveFolder(), "backup");

            if(!backUpFolder.exists()) {
                backUpFolder.mkdirs();
            }

            SimpleDateFormat df = new SimpleDateFormat("MMddHH");

            String name = file.getName() + "-" + df.format(new Date()) + ".bak";

            file.renameTo(new File(backUpFolder, name));
        }
    }


    public void createNote(String filePath, String password) throws Exception {

        String root = "<Note></Note>";
        String aesKey = Md5Crypt.apr1Crypt(password, password);
        String content = AES.encrypt(root, aesKey);

        this.autoBackUp(filePath);
        fileStore.write(filePath, content);
        session = new Session(root, new ArrayList<Account>(), new HashSet<String>(), filePath, aesKey);
    }

    public void addAccount(Account account) throws Exception {
        account.setId(System.currentTimeMillis() + "");

        try {
            String xml = xmlParser.addNode(session.getXml(), account);
            String content = AES.encrypt(xml, session.getPassword());

            this.autoBackUp(session.getFilePath());
            fileStore.write(session.getFilePath(), content);

            this.loadNoteByAesKey(session.getFilePath(), session.getPassword());
        } catch (DocumentException e) {
            throw new Exception("XML文件解析出错", e);
        } catch (Exception e) {
            throw new Exception("操作文件出错", e);
        }
    }

    public void modifyAccount(Account account) throws Exception {
        try {
            String xml = xmlParser.modifyNode(session.getXml(), account);
            String content = AES.encrypt(xml, session.getPassword());

            this.autoBackUp(session.getFilePath());
            fileStore.write(session.getFilePath(), content);

            this.loadNoteByAesKey(session.getFilePath(), session.getPassword());
        } catch (DocumentException e) {
            throw new Exception("XML文件解析出错", e);
        } catch (Exception e) {
            throw new Exception("操作文件出错", e);
        }
    }

    public void deleteAccount(Account account) throws Exception {
        try {
            String xml = xmlParser.delNode(session.getXml(), account);
            String content = AES.encrypt(xml, session.getPassword());

            this.autoBackUp(session.getFilePath());
            fileStore.write(session.getFilePath(), content);

            this.loadNoteByAesKey(session.getFilePath(), session.getPassword());
        } catch (DocumentException e) {
            throw new Exception("XML文件解析出错", e);
        } catch (Exception e) {
            throw new Exception("操作文件出错", e);
        }
    }


    public Set<String> queryTags() {
        return session.getTags();
    }

    public List<Account> queryAccounts(Set<String> tags) {

        List<Account> accounts = new ArrayList<Account>();
        List<Account> sessionAccounts = session.getAccountList();

        for (Account account : sessionAccounts) {

            Set<String> tagSet = account.getTag();

            if(tags == null || tags.size() == 0) {
                accounts.add(account);
            } else if(tagSet != null && tagSet.size() > 0) {
                for(String tag : tags) {
                    if(tagSet.contains(tag)) {
                        accounts.add(account);
                        break;
                    }
                }
            }
        }

        Collections.sort(accounts);
        return accounts;
    }

    public Account queryAccount(String id) {

        List<Account> sessionAccounts = session.getAccountList();

        for(Account ac : sessionAccounts) {

            if(StringUtils.equals(ac.getId(), id)) {
                return ac;
            }
        }

        return null;
    }

    public void loadNoteByAesKey(String filePath, String password) throws Exception {
        this.loadNote(filePath, password, true);
    }

    public void loadNote(String filePath, String password) throws Exception {
        this.loadNote(filePath, password, false);
    }

    public void loadNote(String filePath, String password, boolean isAesKey) throws Exception {

        String content = null;
        String xml = null;
        String aesKey = null;
        try {
            content = fileStore.read(filePath);
        } catch (IOException e) {
            throw new Exception("密码本未找到", e);
        }

        try {
            if(isAesKey) {
                aesKey = password;
            } else {
                aesKey = Md5Crypt.apr1Crypt(password, password);
            }

            xml = AES.decrypt(content, aesKey);
        } catch (Exception e) {
            throw new Exception("密码错误，解密失败", e);
        }

        try {
            List<Account> accountList = xmlParser.parse(xml);

            Set<String> tagSet = new HashSet<String>();

            for (Account account : accountList) {
                Set<String> tags = account.getTag();

                for (String tag : tags) {
                    tagSet.add(tag);
                }
            }

            session = new Session(xml, accountList, tagSet, filePath, aesKey);
        } catch (DocumentException e) {
            throw new Exception("密码错误，解析失败", e);
        }
    }

    public boolean verifyPassword(String password) throws Exception {

        String aesKey = null;
        try {
            aesKey = Md5Crypt.apr1Crypt(password, password);
        } catch (Exception e) {
            throw new Exception("验证密码错误", e);
        }

        return StringUtils.equals(aesKey, session.getPassword());
    }

    public void modifyPassword(String password)  throws Exception {
        String xml = session.getXml();

        String aesKey = null;
        try {
            aesKey = Md5Crypt.apr1Crypt(password, password);
        } catch (Exception e) {
            throw new Exception("验证密码错误", e);
        }

        String content = null;
        try {
            content = AES.encrypt(xml, aesKey);
        } catch (Exception e) {
            throw new Exception("加密文件出错", e);
        }

        try {
            this.autoBackUp(session.getFilePath());
            fileStore.write(session.getFilePath(), content);
        } catch (IOException e) {
            throw new Exception("修改密码出错", e);
        }

        loadNoteByAesKey(session.getFilePath(), aesKey);
    }

    public void logoout() {
        session = null;
    }

}
