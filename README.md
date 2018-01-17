# district-ui-server-config

[![Build Status](https://travis-ci.org/district0x/district-ui-server-config.svg?branch=master)](https://travis-ci.org/district0x/district-ui-server-config)

Clojurescript [mount](https://github.com/tolitius/mount) + [re-frame](https://github.com/Day8/re-frame) module for a district UI, that loads config from a server. 

## Installation
Add `[district0x/district-ui-server-config "1.0.0"]` into your project.clj  
Include `[district.ui.server-config]` in your CLJS file, where you use `mount/start`

## API Overview

**Warning:** district0x modules are still in early stages, therefore API can change in a future.

- [district.ui.server-config](#districtuiserver-config)
- [district.ui.server-config.subs](#districtuiserver-configsubs)
  - [::config](#config-sub)
- [district.ui.server-config.events](#districtuiserver-configevents)
  - [::load-config](#load-config)
  - [::set-config](#set-config)
- [district.ui.server-config.queries](#districtuiserver-configqueries)
  - [config](#config)
  - [merge-config](#merge-config)

## district.ui.server-config
This namespace contains server-config [mount](https://github.com/tolitius/mount) module.

You can pass following args to initiate this module: 
* `:default` Default config before loading 
* `:url` Url to load config from. Default: `/config`
* `:format` Format of a server response. Options are `:transit` or `:json`. Default: `:transit`.
* `:request-timeout` Timeout of a request
* `:disable-loading-at-start?` Skip loading at mount start

```clojure
  (ns my-district.core
    (:require [mount.core :as mount]
              [district.ui.server-config]))

  (-> (mount/with-args
        {:server-config {:default {:something 10}}})
    (mount/start))
```

## district.ui.server-config.subs
re-frame subscriptions provided by this module:

#### <a name="config-sub">`config`
Returns config. You can pass additional keys, which will do `get-in` into config. 

```clojure
(ns my-district.core
    (:require [mount.core :as mount]
              [district.ui.server-config.subs :as config-subs]))
  
  (defn home-page []
    (let [all-configs (subscribe [::config-subs/config])
          cats-config (subscribe [::config-subs/config :cats])
          small-doggos-config (subscribe [::config-subs/config :doggos :small])]  
      (fn []
        [:div "All configs: " @all-configs]
        [:div "Cats config: " @cats-config]
        [:div "Small doggos config: " @small-doggos-config])))
```

## district.ui.server-config.events
re-frame events provided by this module:

#### <a name="load-config">`load-config [opts]`
Loads config from a server. Pass same opts as to `mount/with-args`

#### <a name="set-config">`set-config [new-config]`
Sets new config. Fired when loaded a server response. You can use this event to hook into event flow, e.g with [re-frame-forward-events-fx](https://github.com/Day8/re-frame-forward-events-fx).

## district.ui.server-config.queries
DB queries provided by this module:  
*You should use them in your events, instead of trying to get this module's 
data directly with `get-in` into re-frame db.*

#### <a name="config">`config [db & ks]`
Works the same way as sub `::config`

#### <a name="merge-config">`merge-config [db new-config]`
Merges config and returns new re-frame db.

## Development
```bash
lein deps

# To run tests and rerun on changes
lein doo chrome tests
```