(ns send-phone-api.socket.websocket-clients)

(def websockets (atom {}))


(defn get-all-websockets []

  @websockets
  )

(defn add-websocket! [id channel]

  (swap! websockets assoc id {:id id :channel channel})

  )

(defn remove-websocket! [id]


  (swap! websockets dissoc id)
  )

(defn get_websocket [client-id]

  (get-in (get-all-websockets) [client-id])

  )
