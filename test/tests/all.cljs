(ns tests.all
  (:require
    [cljs.test :refer [deftest is testing run-tests async use-fixtures]]
    [day8.re-frame.test :refer [run-test-async wait-for run-test-sync]]
    [district.ui.server-config.events :as events]
    [district.ui.server-config.subs :as subs]
    [district.ui.server-config]
    [mount.core :as mount]
    [re-frame.core :refer [reg-event-fx dispatch-sync subscribe reg-cofx reg-fx dispatch]]))

(reg-fx
  :http-xhrio
  (fn [{:keys [:on-success]}]
    (dispatch (vec (concat on-success [{:a 60 :b {:c 90}}])))))

(use-fixtures
  :each
  {:after
   (fn []
     (mount/stop))})

(deftest tests
  (run-test-async
    (let [config (subscribe [::subs/config])
          config-a (subscribe [::subs/config :a])
          config-bc (subscribe [::subs/config :b :c])]

      (-> (mount/with-args
            {:server-config {:default {:a 1 :d 2}}})
        (mount/start))

      (wait-for [::events/set-config ::events/config-load-failed]
        (is (= {:a 60 :b {:c 90} :d 2} @config))
        (is (= @config-a 60))
        (is (= @config-bc 90))))))

