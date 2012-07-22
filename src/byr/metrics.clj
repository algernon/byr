(ns byr.metrics
  (:use [aleph.http]
        [clojure.data.json]))

(def metric-worker (agent nil))
(def app-name (System/getenv "BYR_METRIC_APP_NAME"))
(def api-key (System/getenv "RESTFUL_METRICS_API_KEY"))

(defn metric-request
  [name]

  (sync-http-request {:method :post
                      :url (str "http://track.restfulmetrics.com/apps/" app-name "/metrics.json")
                      :headers {"Authorization" api-key}
                      :content-type "application/json"
                      :body (json-str {:metric {:name name
                                                :value 1}})}))

(defn fire-metric
  [name]

  (send metric-worker (metric-request name)))
