(ns byr.web
  (:use [compojure.core])
  (:require [byr.db :as db]
            [ring.adapter.jetty :as ring]
            [ring.util.response :as resp]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(def base-url (System/getenv "BYR_BASE"))

(defn- byr-url-get [id]
  (try
    (db/url-for id)
    (catch Exception e
      nil)))

(defn- byr-redirect [id]
  (let [url (byr-url-get id)]
    (cond
     (nil? url) nil
     :else (resp/redirect url))))

(defn- byr-add-uri
  [& params]
  (try
    (let [r (apply db/add-map params)]
      (-> (str base-url (:_id r))
          resp/response
          (resp/content-type "text/plain")))

    (catch Exception e
      (-> "id already exists"
          resp/response
          (resp/content-type "text/plain")
          (resp/status 409)))))

(defroutes byr-routes
  (GET "/" [] (resp/content-type 
               (resp/resource-response "index.html" {:root "public"})
               "text/html"))
  (GET "/+/:longurl" [longurl] (byr-add-uri longurl))
  (GET "/:id" [id] (byr-redirect id))
  (POST "/" [longurl] (byr-add-uri longurl))
  (POST "/:id" [id longurl] (byr-add-uri longurl id))
  (route/not-found (-> "Not found"
                       resp/response
                       (resp/content-type "text/plain"))))

(def app
  (handler/site byr-routes))

(defn start [port]
  (ring/run-jetty #'app {:port (or port 8080) :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
