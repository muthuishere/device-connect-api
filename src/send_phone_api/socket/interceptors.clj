(ns send-phone-api.socket.interceptors
  (:require

    [send-phone-api.sessions :as sessions]
    [send-phone-api.socket.sockets :as sockets]
    [send-phone-api.socket.wrapper :refer :all]
    [clojure.tools.logging :refer [info]]


    ))



(defn login [id message ]


  (info "logging in")
  ;remove from sessions
  ;(sessions/remove-session id)

  ;update login status of client
  (sockets/update-websocket-status-to-log-in! id)

  )

(defn close-channel-with-id [client-id]

  )


(defn logout [id message ]

  ;remove from clients
  ;(sockets/remove-websocket! id)



  (let [socket (sockets/get-websocket id)]

    (if  socket (close-channel  (:channel socket) ))

    )


  )


(def interceptors  [
                    {:action "login"          :interceptor login}
                    {:action "logout"          :interceptor logout}

                    ]
  )

(defn get-interceptor-filter[key]

  (first (filter #(= key (get % :action)) interceptors)))


(defn get-interceptor[key]
  (get (get-interceptor-filter key) :interceptor))


(defn intercept-message [client-id message ]
  (info "message" message)
  (info ":action" (:action message))
  (info "get-interceptor" (get-interceptor (:action message)))

  (let [interceptor  (get-interceptor (:action message))]

    (info "interceptor" interceptor)
    (if  interceptor  (interceptor client-id message ) )

    )



  )