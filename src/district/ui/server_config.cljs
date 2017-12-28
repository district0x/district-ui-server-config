(ns district.ui.server-config
  (:require
    [cljs.spec.alpha :as s]
    [district.ui.server-config.events :as events]
    [mount.core :as mount :refer [defstate]]
    [re-frame.core :refer [dispatch-sync]]))

(declare start)
(declare stop)
(defstate server-config
          :start (start (:server-config (mount/args)))
          :stop (stop))


(s/def ::request-timeout number?)
(s/def ::format keyword?)
(s/def ::disable-loading-at-start? boolean?)
(s/def ::default map?)
(s/def ::opts (s/nilable (s/keys :opt-un [::disable-loading-at-start? ::default
                                          ::format ::request-timeout])))

(defn start [opts]
  (dispatch-sync [::events/start opts])
  opts)


(defn stop []
  (dispatch-sync [::events/stop]))
