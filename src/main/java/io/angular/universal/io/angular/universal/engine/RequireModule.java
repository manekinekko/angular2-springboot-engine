package io.angular.universal.io.angular.universal.engine;

import jdk.nashorn.api.scripting.NashornScriptEngine;
import org.springframework.web.context.request.async.NoSupportAsyncWebRequest;

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Created by wchegham on 07/02/16.
 */
public class RequireModule {

    private NashornScriptEngine nashornScriptEngine;

    //https://github.com/facebook/react/issues/3037
    private String polyfill = "" +
        "var global = this;     \n" +
        "var console = {};      \n" +
        "console.debug = print; \n" +
        "console.warn = print;  \n" +
        "console.log = print;";

    public RequireModule(){
        nashornScriptEngine = (NashornScriptEngine) new ScriptEngineManager().getEngineByName("nashorn");
    }

    public NashornScriptEngine universal() {
        try {
            nashornScriptEngine.eval(polyfill);
            nashornScriptEngine.eval(read("static/node_modules/angular2-universal-preview/dist/server/index.js"));
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        return nashornScriptEngine;
    }


    public NashornScriptEngine angular() {
        try {
            nashornScriptEngine.eval(polyfill);
            nashornScriptEngine.eval(read("static/node_modules/es6-shim/es6-shim.min.js"));
            nashornScriptEngine.eval(read("static/node_modules/systemjs/dist/system-polyfills.js"));
            nashornScriptEngine.eval(read("static/node_modules/angular2/bundles/angular2-polyfills.min.js"));
            nashornScriptEngine.eval(read("static/node_modules/systemjs/dist/system.js"));
            nashornScriptEngine.eval(read("static/node_modules/rxjs/bundles/Rx.js"));
            nashornScriptEngine.eval(read("static/node_modules/angular2/bundles/angular2.dev.js"));
            nashornScriptEngine.eval(read("static/node_modules/angular2/bundles/router.dev.js"));
            nashornScriptEngine.eval(read("static/node_modules/angular2/bundles/http.dev.js"));
            nashornScriptEngine.eval("System.config({'baseURL':'/','defaultJSExtensions':true});");
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
        return nashornScriptEngine;
    }

    protected Reader read(String path) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(in);
    }

}
