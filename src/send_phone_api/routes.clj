(ns send-phone-api.routes   (:require
                                      [compojure.core :refer :all]
                                      [clojure.tools.logging :refer [info]]
                                      [compojure.route :as route]
                                      [send-phone-api.api.api-handlers :refer [send-to-websocket get-all-websockets create-session ]]
                                      [send-phone-api.socket.websocket-handlers :refer [socket-handler]]
                                     )
)

(defroutes app-routes
           (GET "/websocket/:id" [id] (fn [req]
                                        (socket-handler id req)
                                        ))

           (GET "/send-to-client/:id" [id] (fn [req]
                                             (send-to-websocket id req)
                                             ))

           (GET "/createsession" []  create-session)
           (GET "/get-all-websockets" []  get-all-websockets)

           (route/not-found "Error, page not found!"))
