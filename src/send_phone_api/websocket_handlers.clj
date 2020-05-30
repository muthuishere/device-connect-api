(ns send-phone-api.websocket-handlers
  (:require [org.httpkit.server :refer (send! on-close with-channel on-receive)]

            [clojure.tools.logging :refer [info]]

            [clojure.data.json :refer [read-str json-str read-json write-str]]
            )
  (:gen-class))

(def clients (atom {}))


(defn get-all-active-sockets []

  @clients
  )


(defn send-to-client-socket [client-id msg]

  (info client-id)
  (doseq [client (vals (get-all-active-sockets))]
    ;; send all, client will filter them
    (info client)
    (info (:id client))
    (info (:channel client))

    (if (= client-id (:id client)) (send! (:channel client) msg) (println "check again"))))



(defn message-received [msg channel]
  (let [data (read-str msg)]
    (let [response {:src data :echo "received"}]


      (info "mesg received" data)
      (send! channel (write-str response))

      )))


(defn socket-handler [id req]

  (info id "connected")

  (with-channel req channel
                (info channel "connected")
                (swap! clients assoc id {:id id :channel channel})
                (on-receive channel (fn [msg]
                                      (message-received msg channel)
                                      ))
                (on-close channel (fn [status]
                                    (swap! clients dissoc id)
                                    (info channel "closed, status" status)))))


(defn get-dummy-channel []

  {:channel "dummuy"}
  )

(defn test-socket-handler [id req]

  (info id "connected")
  (swap! clients assoc id {:id id :channel (get-dummy-channel)})

  )
