(ns byr.db
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [byr.utils :as utils]))

(defn init []
  (let [db-uri (System/getenv "MONGOLAB_URI")]
    (if (nil? db-uri)
      (mg/connect!)
      (mg/connect-via-uri! db-uri))))

(defn shorten [url]
  (let [counter (mc/find-and-modify "idspool" {:_id "current"}
                                    {"$inc" {:value 1}} :return-new true :upsert true)]
    (cond
      (nil? (:value counter)) "0"
      :else (utils/int->base62 (:value counter)))))

(defn add-map
  ([url] (add-map url (shorten url)))
  ([url id] (mc/insert-and-return "urls" {:_id id, :url url})))

(defn url-for
  [id]

  (.get (mc/find-one "urls" {:_id id} [:url]) "url"))

(init)
