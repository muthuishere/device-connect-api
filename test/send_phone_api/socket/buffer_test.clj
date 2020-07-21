(ns send-phone-api.socket.buffer-test
  (:require [clojure.test :refer :all]
            [send-phone-api.socket.sockets :as websocket-clients]
            [send-phone-api.mocks.shared :refer :all]

            [clojure.tools.logging :refer [info]]
            [send-phone-api.socket.handler :as websocket-handlers]))




(defn test-setup [f]
  (close-all-sockets!)
  (create-test-sockets!)
  (with-redefs [websocket-handlers/send-to (fn [socket msg] (info "dummy " msg) { })]
    (f)
    )
  (close-test-sockets!)
  )

; Here we register my-test-fixture to be called once, wrapping ALL tests
; in the namespace
(use-fixtures :once test-setup)



(deftest test-create-sockets-and-remove
  (testing "creating sockets and removing it"


    (let [
          socket-id (get-a-test-socket-id)
          socket (websocket-clients/get-websocket socket-id)
          ]

      (is (not-empty  socket))

      ;remove socket

      (websocket-clients/remove-websocket! socket-id)


      (is (nil? (websocket-clients/get-websocket socket-id)))
      )

    ))


