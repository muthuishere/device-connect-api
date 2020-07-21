(ns send-phone-api.routes   (:require
                                      [compojure.core :refer :all]
                                      [clojure.tools.logging :refer [info]]
                                      [clojure.data.json :refer (read-str)]
                                      [compojure.route :as route]
                                      [send-phone-api.api.socket :refer :all]
                                      [send-phone-api.api.session :refer :all]
                                      [send-phone-api.socket.handler :refer [socket-handler open-socket]]
                                     )
)

(defn parse [body]

  (let [ js-string  (.trim (slurp body))]

    (if-not (clojure.string/blank? js-string)


    (read-str js-string :key-fn keyword)
    {})
  ))




(defroutes app-routes
           (GET "/websocket/:id" [id] (fn [req]
                                        (socket-handler id req)
                                        ))

           (POST "/send-toclient/:id" [] (fn [req]

                                            (let [
                                                  id (:id (:params req))
                                                  message (parse (:body req))

                                                  ]

                                             (send-to-websocket {:id id :message message})
                                             )))
           (GET "/register" []  create-session)
           (GET "/get-all-websockets" []  get-all-websockets)
           (GET "/get-all-sessions" []  get-all-sessions)

           (route/not-found "Error, page not found!"))
