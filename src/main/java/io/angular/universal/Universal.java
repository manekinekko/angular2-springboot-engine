/**
 * Created by wchegham on 07/02/16.
 */
package io.angular.universal;


import io.angular.universal.io.angular.universal.engine.RequireModule;
import jdk.nashorn.api.scripting.NashornScriptEngine;

import java.util.List;

public class Universal {

    NashornScriptEngine engine;

    Universal() {
        this.engine = (new RequireModule()).angular();
    }

    public String render() {
        try {
            String html = ":)";
            return String.valueOf(html);
        } catch (Exception e) {
            throw new IllegalStateException("failed to render angular component", e);
        }
    }

}