(ns send-phone-api.core
   (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [send-phone-api.routes :refer [app-routes]]
             [clojure.tools.logging :refer [info]]
            [compojure.route :as route]
             [clojure.data.json :refer [json-str read-json]]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
              [send-phone-api.socket.websocket-handlers :refer [socket-handler]]
            [clojure.data.json :as json])
  (:gen-class))





; Our main routes


; Our main entry function
(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
