var browser_d_1 = require('../node_modules/angular2/platform/browser.d');
var router_d_1 = require('../node_modules/angular2/router.d');
var http_d_1 = require('../node_modules/angular2/http.d');
// import {
//   NG_PRELOAD_CACHE_PROVIDERS,
//   PRIME_CACHE
// } from '../../../../modules/universal/client/client';
var app_1 = require('./app');
function main() {
    return browser_d_1.bootstrap(app_1.TodoApp, [
        router_d_1.ROUTER_PROVIDERS,
        http_d_1.HTTP_PROVIDERS,
    ]);
}
exports.main = main;
//# sourceMappingURL=browser.js.map