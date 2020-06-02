(ns send-phone-api.socket.interceptors
  (:require

    [send-phone-api.sessions :as sessions]
    [send-phone-api.socket.websocket-clients :as clients]


    ))



(defn login [client-id message ]

  ;remove from sessions
  (sessions/remove-session client-id)

  ;update login status of client
  (clients/update-websocket-status-to-log-in! client-id)

  )



(def interceptors  [{:action "login"          :interceptor login}]
  )

(defn get-interceptor-filter[key]

  (first (filter #(= key (get % :action)) interceptors)))


(defn get-interceptor[key]
  (get (get-interceptor-filter key) :interceptor))


(defn intercept-message [client-id message ]


  (let [interceptor  (get-interceptor (:action message))]

    (if (not-empty interceptor)  (interceptor client-id message ) )

    )



  )