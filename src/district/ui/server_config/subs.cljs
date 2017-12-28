(ns district.ui.server-config.subs
  (:require
    [district.ui.server-config.queries :as queries]
    [re-frame.core :refer [reg-sub]]))

(defn- sub-fn [query-fn]
  (fn [db [_ & args]]
    (apply query-fn db args)))

(reg-sub
  ::config
  (sub-fn queries/config))
