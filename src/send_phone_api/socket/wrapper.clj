(ns send-phone-api.socket.wrapper
  (:require
    [org.httpkit.server :refer (send! on-close with-channel on-receive close)]
    [clojure.tools.logging :refer [info]]
  ))



(defn close-channel [channel]

  (close channel)

  )


(defn send-to-channel [channel msg]

  (info "sending to channel")
  (send! channel msg)

  )


