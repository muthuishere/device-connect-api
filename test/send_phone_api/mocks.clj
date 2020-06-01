(ns send-phone-api.mocks
  (:require
    [clojure.test :refer :all]
    [clojure.tools.logging :refer [info]]
    [send-phone-api.socket.websocket-clients :as socket-clients ]
    ))

(defn create-socket [name]

  {:send! (fn [req]
            (info "sending " name)
            )}
  )


(defn get-test-socket-ids []
  ["ar" "al"]
  )

(defn get-a-test-socket-id []
  "ar"
  )

(defn create-sockets! []

  (doseq [id (get-test-socket-ids)          ]

    (socket-clients/add-websocket! id (create-socket id))

         )


  )