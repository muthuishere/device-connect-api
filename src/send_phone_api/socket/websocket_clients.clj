(ns send-phone-api.socket.websocket-clients)

(def websockets (atom {}))

(def STATUS {:waiting 0 :loggedIn 1})

(defn get-all-websockets []

  @websockets
  )

(defn add-websocket! [id channel]

  (swap! websockets assoc id {:id id :channel channel :status (:waiting STATUS) })

  )

(defn update-websocket-status-to-log-in! [id]

  (swap! websockets assoc-in  [id :status]   (:loggedIn STATUS ))



  )

(defn remove-websocket! [id]

  (swap! websockets dissoc [id])
  )

(defn get_websocket [client-id]

  (get-in (get-all-websockets) [client-id])

  )
