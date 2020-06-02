(ns send-phone-api.socket.websocket-handlers
  (:require [org.httpkit.server :refer (send! on-close with-channel on-receive)]
            [clojure.tools.logging :refer [info]]
            [send-phone-api.socket.websocket-clients :refer [get-all-websockets get_websocket add-websocket! remove-websocket!]]
            [send-phone-api.socket.interceptors :refer [intercept-message]]

            [clojure.data.json :refer [read-str json-str read-json write-str]]
            [send-phone-api.sessions :as sessions])
  )



(defn send-to [socket msg]

  (send! (:channel socket) (write-str msg))

  )

(defn validate-and-send [client-id socket msg]
  ;

  (intercept-message client-id msg)
  (send-to socket msg)
  )
(defn send-to-client-socket [client-id msg]

  (let [socket (get_websocket client-id)]

    (if (not-empty socket) (validate-and-send client-id socket msg))

    (not-empty socket)

    ))

(defn socket-exists [client-id]

  (not-empty (get_websocket client-id))
  )



(defn message-received [msg channel]
  (let [data (read-str msg)]
    (let [response {:src data :echo "received"}]


      (info "mesg received" data)
      (send! channel (write-str response))

      )))


(defn open-socket [id req]
  (info id "connected")
  (with-channel req channel
                (info channel "connected")
                (add-websocket! id channel)
                (on-receive channel (fn [msg]
                                      (message-received msg channel)
                                      ))
                (on-close channel (fn [status]
                                    (remove-websocket! id)
                                    (info channel "closed, status" status))))

  )
(defn socket-handler [id req]

  (let [session (sessions/get-session id)]
    (if (not-empty session) (open-socket id req)

                            )))



