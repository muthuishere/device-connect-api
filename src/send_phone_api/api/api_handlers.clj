(ns send-phone-api.api.api-handlers
  (:require
    [clojure.data.json :refer (write-str)]
    [clojure.tools.logging :refer [info]]
    [send-phone-api.socket.websocket-handlers :refer [send-to-client-socket]]
    [send-phone-api.socket.websocket-clients :as websockets]
    [send-phone-api.sessions :as sessions]
    [send-phone-api.http_responses :refer [success-json success-string bad-request]]
    ))




(defn create-session [req]
  (info req)

  (let [response (sessions/create-session)]
    (sessions/add-to-global-session response)
    (info response)
    (success-json response)
    ))

(defn get-all-websockets [req]
  (success-json (websockets/get-all-websockets))
  )

(defn get-all-sessions [req]
  (success-json (sessions/get-all-sessions))
  )

(defn send-to-websocket [client-id req]
  (if (send-to-client-socket client-id "A message")
    (success-string "message sent")
    (bad-request "Invalid Client id"))
  )



