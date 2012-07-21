(ns byr.web
  (:use [compojure.core])
  (:require [byr.db :as db]
            [ring.adapter.jetty :as ring]
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
      :else {:status 302
             :headers {"Location" url}})))

(defn- byr-list-urls
  []

  {:headers {"Content-Type" "text/plain"}
   :body "Not implemented yet"})

(defn- byr-add-uri
  ([uri]
     
     (let [r (db/add-map uri)]
       {:headers {"Content-Type" "text/plain"}
        :body (str base-url (:_id r))}))

  ([id uri]

     (try
       (let [r (db/add-map uri id)]
         {:headers {"Content-Type" "text/plain"}
          :body (str base-url (:_id r))})

       (catch Exception e
         {:status 409
          :headers {"Content-Type" "text/plain"}
          :body "id already exists"}))))

(defn- byr-root
  [p]

  (if (nil? (:longurl p))
    (byr-list-urls)
    (byr-add-uri (:longurl p))))

(defroutes byr-routes
  (GET "/" [:as {p :params}] (byr-root p))
  (GET "/:id" [id] (byr-redirect id))
  (POST "/" [longurl] (byr-add-uri longurl))
  (POST "/:id" [id longurl] (byr-add-uri id longurl))
  (route/not-found {:headers {"Content-Type" "text/plain"} :body "Not found"}))

(def app
  (handler/site byr-routes))

(defn start [port]
  (ring/run-jetty #'app {:port (or port 8080) :join? false}))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") "8080"))]
    (start port)))
