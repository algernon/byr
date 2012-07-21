(defproject byr "0.1.0-SNAPSHOT"
  :description "byr -- a simplistic URL shortener"
  :url "http://5ms.in/byr"
  :license {:name "GNU GPL v3+"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.novemberain/monger "1.1.1"]
                 [ring/ring-jetty-adapter "1.1.1"]
                 [compojure "1.1.1"]]
  :plugins [[lein-swank "1.4.4"]
            [lein-ring "0.7.1"]]
  :ring {:handler byr.web/app})
