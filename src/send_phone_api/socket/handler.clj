(ns send-phone-api.socket.handler
  (:require
    [clojure.tools.logging :refer [info]]
    [send-phone-api.socket.sockets :refer [get-all-websockets get-websocket add-websocket! remove-websocket!]]
    [send-phone-api.socket.interceptors :refer [intercept-message]]
    [send-phone-api.socket.wrapper :refer :all]
    [org.httpkit.server :refer (send! on-close with-channel  on-receive close)]

    [clojure.data.json :refer [read-str json-str read-json write-str]]
    [send-phone-api.sessions :as sessions]
    [org.httpkit.server :as server])
  )









(defn send-to [socket msg]
  (info "sending to channel before")
  (send-to-channel (:channel socket) (write-str msg))

  )
(defn intercept-and-send [client-id socket message]
  (info "sending to intercept-and-send")
  (send-to socket message)
  (intercept-message client-id message)

  )
(defn validate-and-send [client-id socket message]



  (let [action  (:action message) ]

    (info "sending to validate-and-send" action)
    (if (not-empty action)   (intercept-and-send client-id socket message ))

    )



  )
(defn send-to-client-socket [client-id message]

  (let [socket (get-websocket client-id)]

    (info socket)
    (if (not-empty socket) (validate-and-send client-id socket message))

    (not-empty socket)

    ))

(defn socket-exists [client-id]

  (not-empty (get-websocket client-id))
  )



(defn on-message-received [msg channel]
  (let [data (read-str msg)]
    (let [response {:src data :echo "received"}]


      (info "mesg received" data)


      (send-to-channel channel (write-str response))


      )))

(defn on-closed [id status ]

  (remove-websocket! id)
  (info "closed, status" status)

  )
(def clients (atom {}))


(defn mesg-received [msg]
  (let [data (read-json msg)]
    (info "mesg received" data)

    (doseq [client (keys @clients)]
      ;; send all, client will filter them
      (server/send! client "Sending again"))))


(defn open-socket-working [id request]
  (println "chat-handler" id)
  (with-channel request channel
                (on-close channel (fn [status] (println "channel closed: " status)))
                (on-receive channel (fn [data] ;; echo it back
                                      (send! channel data)))))



(defn open-socket [id req]

  (with-channel req channel

                (on-receive channel (fn [msg]
                                      (on-message-received msg channel)
                                      ))
                (on-close channel (fn [status]
                                    (on-closed id status)
                                    ))
                (println "adding websocket")
                (add-websocket! id channel)
                )

  ;TODO we might need to manage sessions in db
  ;(sessions/remove-session id)
)



(defn validate-and-open-socket [id req]
  (println "validate-and-open-socket")
  (println (socket-exists id))
  (println (nil?  (socket-exists id)))
  ;if socket id does not exists use it or else open

  (if (nil?  (socket-exists id))
    (open-socket id req)
    (println "Socket already exists")
    )
  )


(defn socket-handler [id req]

  (println "Socket handler")
  (let [session (sessions/get-session id)]
    (if (not-empty session)   (validate-and-open-socket id req) (println "Invalid Session ID"))

                            ))



