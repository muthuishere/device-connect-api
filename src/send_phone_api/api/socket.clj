(ns send-phone-api.api.socket
  (:require
    [clojure.data.json :refer (write-str)]
    [clojure.tools.logging :refer [info]]
    [send-phone-api.socket.websocket-handlers :refer [send-to-client-socket socket-exists]]
    [send-phone-api.socket.worker :refer [put-to-channel]]
    [send-phone-api.socket.websocket-clients :as websockets]
    [send-phone-api.sessions :as sessions]
    [send-phone-api.http_responses :refer [success-json success-string bad-request success-raw]]
    ))

(defn get-all-websockets [req]
  (success-raw (websockets/get-all-websockets))
  )



(defn put-to-queue [id message]
  (info "sending to channel")
  (put-to-channel id message)
  (success-string "message sent")

  )



(defn send-to-websocket [input]


  (let [
        id (:id input)
        message (:message input)

        ]

    (info "id "  id )
    (info "send-to-websocket message "  message )

    (if (socket-exists id)
      (put-to-queue id message)
      (bad-request "Invalid Client id"))
    ))


