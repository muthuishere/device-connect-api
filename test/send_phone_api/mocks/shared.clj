(ns send-phone-api.mocks.shared
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :refer [info]]
            [send-phone-api.socket.sockets :as socket-clients ]
            [send-phone-api.sessions :as sessions ]
            [ring.mock.request :as mock]
            ))



(defn create-test-socket! [name]

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

(defn create-test-sockets! []

  (doseq [id (get-test-socket-ids)          ]

    (socket-clients/add-websocket! id (create-test-socket! id))

    )


  )

(defn close-test-sockets! []

  (doseq [id (get-test-socket-ids)          ]

    (socket-clients/remove-websocket! id )

    )


  )

(defn close-all-sockets! []

  (doseq [id (socket-clients/get-all-websockets)          ]

    (socket-clients/remove-websocket! id )

    )


  )





(defn get-a-test-session-id []

  (:sessionId (sessions/create-session))


  )

(defn create-test-session []

  (let [session (sessions/create-session)]

    (sessions/add-to-global-session session)

    (:sessionId  session)
    )



  )

(defn get-a-mock-request []

  (mock/request :get "/doc/10")

  )