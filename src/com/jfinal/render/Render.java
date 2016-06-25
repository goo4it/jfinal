/**
 * Copyright (c) 2011-2016, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jfinal.render;

import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jfinal.core.Const;
import com.jfinal.kit.Prop;
import com.jfinal.kit.StrKit;

/**
 * Render.
 */
public abstract class Render {

    protected String              view;
    protected HttpServletRequest  request;
    protected HttpServletResponse response;
    protected Prop                prop;

    private static String         encoding = Const.DEFAULT_ENCODING;
    private static boolean        devMode  = Const.DEFAULT_DEV_MODE;

    static void init(String encoding, boolean devMode) {
        Render.encoding = encoding;
        Render.devMode = devMode;
    }

    public static String getEncoding() {
        return encoding;
    }

    public static boolean getDevMode() {
        return devMode;
    }

    public Render setContext(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        return this;
    }

    public Render setContext(HttpServletRequest request, HttpServletResponse response, String viewPath) {
        this.request = request;
        this.response = response;
        if (view != null && !view.startsWith("/")) view = viewPath + view;
        return this;
    }

    /**
     * Load property file.
     * Example:<br>
     * loadPropertyFile("db_username_pass.txt", "UTF-8");
     * 
     * @param fileName
     *            the file in CLASSPATH or the sub directory of the CLASSPATH
     * @param encoding
     *            the encoding
     */
    public Properties loadPropertyFile(String fileName, String encoding) {
        prop = new Prop(fileName, encoding);
        return prop.getProperties();
    }

    /**
     * Load property file.
     * 
     * @see #loadPropertyFile(String, String)
     */
    public Properties loadPropertyFile(String fileName) {
        return loadPropertyFile(fileName, Const.DEFAULT_ENCODING);
    }

    protected String getProperty(String key) {
        if (prop == null) return null;
        return prop.get(key);
    }

    protected String getProperty(String key, String defaultValue) {
        return StrKit.notBlank(getProperty(key)) ? getProperty(key) : defaultValue;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    /**
     * Render to client
     */
    public abstract void render();
}
