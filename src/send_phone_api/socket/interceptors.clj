(ns send-phone-api.socket.interceptors
  (:require [send-phone-api.sessions :as sessions]))



(defn login [client-id message ]

  ;remove from sessions
  (sessions/remove-session client-id)

  ;update login status of client

  )


(def interceptors  [{:action "login"          :interceptor login}]


  )
