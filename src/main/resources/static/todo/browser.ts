import {provide} from '../node_modules/angular2/core.d';

import {bootstrap} from '../node_modules/angular2/platform/browser.d';


import {ROUTER_PROVIDERS} from '../node_modules/angular2/router.d';

import {HTTP_PROVIDERS} from '../node_modules/angular2/http.d';

// import {
//   NG_PRELOAD_CACHE_PROVIDERS,
//   PRIME_CACHE
// } from '../../../../modules/universal/client/client';


import {TodoApp} from './app';

export function main() {
  return bootstrap(TodoApp, [
    ROUTER_PROVIDERS,
    HTTP_PROVIDERS,
    // NG_PRELOAD_CACHE_PROVIDERS,
    // provide(PRIME_CACHE, {useValue: true})
  ]);
}
