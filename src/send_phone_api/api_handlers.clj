(ns send-phone-api.api-handlers
  (:require
    [clojure.data.json :refer (write-str)]
    [clojure.tools.logging :refer [info]]
    [send-phone-api.websocket-handlers :refer [get-all-active-sockets send-to-client-socket]]
    ))


(def all-sessions (atom []))

(defn create-uuid []
  (.toString (java.util.UUID/randomUUID))
  )

(defn create-response [sessionId req]
  {:sessionId sessionId :time (System/currentTimeMillis)}
  )

(defn create-sessionId-response [req]
  (create-response (create-uuid ) req)
  )

(defn add-to-global-session [response]
  (swap! all-sessions conj response   )
  )

(defn get-all-sessions []
  @all-sessions
  )
(defn create-session [req]
  (info req)

  (let [response (create-sessionId-response req) ]
    (add-to-global-session response)
    (info response)
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body    (str (write-str response))}))

(defn get-all-clients [req]

  (info (get-all-active-sockets))
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body     (get-all-active-sockets)})

(defn send-to-client [client-id req]
  (send-to-client-socket client-id "A message")
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body     (write-str {:status "message sent"})})


