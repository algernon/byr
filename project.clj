(defproject byr "0.1.0-SNAPSHOT"
  :description "byr -- a simplistic URL shortener"
  :min-lein-version "2.0.0"
  :url "http://5ms.in/byr"
  :license {:name "GNU GPL v3+"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [com.novemberain/monger "1.1.1"]
                 [ring/ring-jetty-adapter "1.1.1"]
                 [compojure "1.1.1" :exclusions [org.clojure/tools.macro]]
                 [aleph "0.3.0-alpha2"]
                 [org.clojure/data.json "0.1.2"]]
  :plugins [[lein-ring "0.7.1"]]
  :ring {:handler byr.web/app})
