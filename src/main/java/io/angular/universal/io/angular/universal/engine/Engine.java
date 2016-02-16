package io.angular.universal.io.angular.universal.engine;

import com.sun.xml.internal.xsom.impl.scd.Iterators;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.internal.objects.annotations.Function;

import javax.script.ScriptException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wchegham on 07/02/16.
 */
public class Engine {

    private interface IEngineOptions {
        Function App = null;
        Iterators.Array<Object> providers = null;
        Object preboot = null;
        String selector = null;
        String serializedCmp = null;
        boolean server = false;
        boolean client = false;
    }

    private interface IQueryParams {
        boolean preboot = false;
        boolean angular = false;
        boolean bootstrap = false;
        boolean client = false;
        boolean server = false;
        String componentUrl = "";
    }

    private NashornScriptEngine engine;

    static String prebootScript = "  <preboot>" +
            "    <link rel='stylesheet' type='text/css' href='/preboot/preboot.css'>" +
            "    <script src='/preboot/preboot.js'></script>" +
            "    <script>preboot.start()</script>" +
            "  </preboot>";

    static String angularScript = "  <!-- Browser polyfills -->" +
            "  <script src='/node_modules/es6-shim/es6-shim.min.js'></script>" +
            "  <script src='/node_modules/systemjs/dist/system-polyfills.js'></script>" +
            "  <script src='/node_modules/angular2/bundles/angular2-polyfills.min.js'></script>" +
            "  <!-- SystemJS -->" +
            "  <script src='/node_modules/systemjs/dist/system.js'></script>" +
            "  <!-- Angular2: Bundle -->" +
            "  <script src='/node_modules/rxjs/bundles/Rx.js'></script>" +
            "  <script src='/node_modules/angular2/bundles/angular2.dev.js'></script>" +
            "  <script src='/node_modules/angular2/bundles/router.dev.js'></script>" +
            "  <script src='/node_modules/angular2/bundles/http.dev.js'></script>" +
            "  <script type='text/javascript'>" +
            "    System.config({" +
            "      'baseURL': '/'," +
            "      'defaultJSExtensions': true" +
            "    });" +
            "  </script>";
    static String bootstrapButton = "  <div id='bootstrapButton'>" +
            "    <style>" +
            "     #bootstrapButton {" +
            "      z-index:999999999;" +
            "      position: absolute;" +
            "      background-color: rgb(255, 255, 255);" +
            "      padding: 0.5em;" +
            "      border-radius: 3px;" +
            "      border: 1px solid rgb(207, 206, 206);" +
            "     }" +
            "    </style>" +
            "    <button onclick='bootstrap()'>" +
            "      Bootstrap Angular2 Client" +
            "    </button>" +
            "  </div>";
    static String bootstrapApp = "  <script>" +
            "    setTimeout(function() {" +
            "      bootstrap();" +
            "    });" +
            "  </script>";


    Engine() {
        engine = (new RequireModule()).universal();
    }

    public String bootstrapFunction(String appUrl) {
        return "<script>" +
                "    function bootstrap() {" +
                "      if (this.bootstraped) return;" +
                "      this.bootstraped = true;" +
                "      System.import('" + appUrl + "')" +
                "        .then(function(module) {" +
                "          return module.main();" +
                "        })" +
                "        .then(function() {" +
                "          preboot.complete();" +
                "          var $bootstrapButton = document.getElementById('bootstrapButton');" +
                "          if ($bootstrapButton) { $bootstrapButton.remove(); }" +
                "        });" +
                "    }" +
                "  </script>";
    }

    /*
    * TODO: find better ways to configure the App initial state
    * to pay off this technical debt
    * currently checking for explicit values
    * */
    public String buildClientScripts(String html, IQueryParams options) {
        return html
                .replace(
                        this.call("selectorRegExpFactory", "preboot"),
                        ((options.preboot == false) ? "" : this.prebootScript)
                )
                .replace(
                        this.call("selectorRegExpFactory", "angular"),
                        ((options.angular == false) ? "" : this.angularScript)
                )
                .replace(
                        this.call("selectorRegExpFactory", "bootstrap"),
                        ((options.bootstrap == false) ? (
                                this.bootstrapButton +
                                        bootstrapFunction(options.componentUrl)
                        ) : (
                                (
                                        (options.client == false) ? "" : this.bootstrapButton
                                ) +
                                        bootstrapFunction(options.componentUrl) +
                                        ((options.client == false) ? "" : this.bootstrapApp)
                        ))
                );
    }

    private String call(String funcName, Object params) {
        try {
            return (String) this.engine.invokeFunction(funcName, params);
        } catch (ScriptException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected Reader read(String path) {
        InputStream in = getClass().getClassLoader().getResourceAsStream(path);
        return new InputStreamReader(in);
    }

    public String ng2engine(String filePath, IQueryParams queryOptions, IEngineOptions engineOption, Function done) {

        String clientHtml = read(filePath).toString();
        if (queryOptions.server == false && queryOptions.client == false) {
            return clientHtml;
        }
        if (queryOptions.server == false && queryOptions.client != false) {
            return buildClientScripts(clientHtml, queryOptions);
        }

        // bootstrap and render component to string
        String renderPromise = this.call("renderToString", null);
        List<Object> args = new ArrayList<>();
        args.add(engineOption.App);
        args.add(engineOption.providers);

        if (queryOptions.preboot) {
            renderPromise = this.call("renderToStringWithPreboot", null);
            args.add(queryOptions.preboot);
        }

        return this.call("renderPromise", args);

        /*
        // defaults
        options = options || <engineOptions>{};
        options.providers = options.providers || undefined;
        options.preboot   = options.preboot   || undefined;

        // read file on disk
        try {
            fs.readFile(filePath, (err, content) => {

                if (err) { return done(err); }

                // convert to string
                const clientHtml: string = content.toString();

                // TODO: better build scripts abstraction
                if (options.server === false && options.client === false) {
                    return done(null, clientHtml);
                }
                if (options.server === false && options.client !== false) {
                    return done(null, buildClientScripts(clientHtml, options));
                }
                // bootstrap and render component to string
                var renderPromise: any = renderToString;
                const args = [options.App, options.providers];
                if (options.preboot) {
                    renderPromise = renderToStringWithPreboot;
                    args.push(options.preboot);
                }

                renderPromise(...args)
                .then(serializedCmp => {

                const selector: string = selectorResolver(options.App);

                // selector replacer explained here
                // https://gist.github.com/gdi2290/c74afd9898d2279fef9f
                // replace our component with serialized version
                const rendered: string = clientHtml.replace(
                        // <selector></selector>
                        selectorRegExpFactory(selector),
                        // <selector>{{ serializedCmp }}</selector>
                        serializedCmp
                        // TODO: serializedData
                );

                done(null, buildClientScripts(rendered, options));
                })
                .catch(e => {
                        console.log(e.stack);
                // if server fail then return client html
                done(null, buildClientScripts(clientHtml, options));
                });
            });
        } catch (e) {
            done(e);
        }
    */
    };



}
