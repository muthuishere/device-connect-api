(ns send-phone-api.socket.sockets
  (:require
    [clojure.tools.logging :refer [info]]
    ))

(def websockets (atom {}))

(def STATUS {:waiting 0 :loggedIn 1})

(defn get-all-websockets []

  @websockets
  )

(defn add-websocket! [id channel]


  (swap! websockets assoc id {:id id :channel channel :status (:waiting STATUS) })
  (info "adding web sock et" (.size @websockets) )
  )

(defn update-websocket-status-to-log-in! [id]

  (swap! websockets assoc-in  [id :status]   (:loggedIn STATUS ))


  )

(defn remove-websocket! [id]


  (info "before remove web sock et" (.size @websockets) )
  (swap! websockets dissoc id)
  (info "after remove web sock et" (.size @websockets) )
  )

(defn get-websocket [client-id]

  (get-in (get-all-websockets) [client-id])

  )
