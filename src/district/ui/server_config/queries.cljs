(ns district.ui.server-config.queries)

(defn config
  ([db & ks]
    (get-in (config db) ks))
  ([db]
   (-> db :district.ui.server-config)))

(defn merge-config [db config]
  (update db :district.ui.server-config merge config))

(defn dissoc-server-config [db]
  (dissoc db :district.ui.server-config))
