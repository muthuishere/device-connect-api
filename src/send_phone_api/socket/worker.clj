(ns send-phone-api.socket.worker
  (:require [clojure.core.async
             :refer [>! <! >!! <!! go chan buffer close! thread
                     alts! alts!! take! put! timeout go-loop]]
            [clojure.tools.logging :refer [info]]
            [send-phone-api.socket.handler :refer [send-to-client-socket]]
            )
  (:gen-class))


;1024 messages to hold
  (def message-channel (chan 1024))


  (defn put-to-channel [id message]

    (info "sent to channel")
    (go (>! message-channel {:id id :message message}))


    )

  (defn init-socket-channel []

    (go-loop []
             (let [packet (<! message-channel)]


               (info "reading to channel" packet)
                 (send-to-client-socket (:id packet) (:message packet))
               )
             (recur))


    )