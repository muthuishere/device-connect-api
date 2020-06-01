(ns send-phone-api.routes   (:require
                                      [compojure.core :refer :all]
                                      [clojure.tools.logging :refer [info]]
                                      [clojure.data.json :refer (read-str)]
                                      [compojure.route :as route]
                                      [send-phone-api.api.api-handlers :refer [login-to-websocket send-to-websocket get-all-websockets create-session ]]
                                      [send-phone-api.socket.websocket-handlers :refer [socket-handler]]
                                     )
)

(defn parse [body]

  (let [ js-string  (.trim (slurp body))]

    (info js-string)

    (if-not (clojure.string/blank? js-string)


    (read-str js-string :key-fn keyword)
    {})
  ))




(defroutes app-routes
           (GET "/websocket/:id" [id] (fn [req]
                                        (socket-handler id req)
                                        ))
           (POST "/send-to-client/:id" [] (fn [req]

                                            (let [
                                                  id (:id (:params req))
                                                  message (parse (:body req))

                                                  ]


                                            (info "id "  id )
                                            (info "body "  message )
                                             (send-to-websocket {:id id :message message})
                                             )))
           (POST "/authenticate/:id" [] (fn [req]

                                            (let [
                                                  id (:id (:params req))
                                                  message (parse (:body req))

                                                  ]

                                             (login-to-websocket {:id id :message message})
                                             )))
           (GET "/createsession" []  create-session)
           (GET "/get-all-websockets" []  get-all-websockets)

           (route/not-found "Error, page not found!"))
