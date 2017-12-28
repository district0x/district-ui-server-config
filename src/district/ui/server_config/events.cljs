(ns district.ui.server-config.events
  (:require
    [ajax.core :as ajax]
    [day8.re-frame.http-fx]
    [district.ui.server-config.queries :as queries]
    [re-frame.core :refer [reg-event-fx trim-v]]))

(def interceptors [trim-v])

(reg-event-fx
  ::start
  interceptors
  (fn [{:keys [:db]} [{:keys [:default :disable-loading-at-start?] :as opts}]]
    (merge
      {:db (queries/merge-config db (or default {}))}
      (when-not disable-loading-at-start?
        {:dispatch [::load-config opts]}))))


(reg-event-fx
  ::load-config
  interceptors
  (fn [{:keys [:db]} [{:keys [:url :request-timeout :format]
                       :or {url "/config"
                            request-timeout 10000
                            format :transit}}]]
    {:http-xhrio {:method :get
                  :uri url
                  :timeout request-timeout
                  :response-format (condp = format
                                     :json (ajax/json-response-format {:keywords? true})
                                     (ajax/transit-response-format))
                  :on-success [::set-config]
                  :on-failure [::config-load-failed]}}))


(reg-event-fx
  ::set-config
  interceptors
  (fn [{:keys [:db]} [new-config]]
    {:db (queries/merge-config db new-config)}))


(reg-event-fx
  ::config-load-failed
  (constantly nil))


(reg-event-fx
  ::stop
  interceptors
  (fn [{:keys [:db]}]
    {:db (queries/dissoc-server-config db)}))

