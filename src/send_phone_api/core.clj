(ns send-phone-api.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [send-phone-api.routes :refer [app-routes]]
            [clojure.tools.logging :refer [info]]
            [ring.middleware.reload :refer [wrap-reload]]
            [clojure.data.json :refer [json-str read-json]]
            [ring.middleware.defaults :refer :all]
            [send-phone-api.socket.handler :refer [socket-handler]]
            [send-phone-api.socket.worker :refer [init-socket-channel]]
       )
  (:gen-class))





(defn init-app []

  (init-socket-channel)
  )
; Our main routes

(defn -dev-main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8090"))]

    (init-app)
    ; Run the server with Ring.defaults middleware
    (server/run-server  ( wrap-reload #'app-routes api-defaults) {:port port :join? false})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))




; Our main entry function
(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "8090"))]

    (init-app)
    ; Run the server with Ring.defaults middleware
    (server/run-server ( wrap-defaults #'app-routes api-defaults) {:port port})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
