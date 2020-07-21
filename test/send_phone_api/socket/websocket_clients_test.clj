(ns send-phone-api.socket.websocket-clients-test
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


(deftest test-creating-sockets
  (testing "creating sockets should update web-socket-clients"


    (let [sockets (websocket-clients/get-all-websockets)]


      (info (.size sockets))

      (is (= 2  (.size sockets)))



      )

    ))

(deftest test-find-socket
  (testing "creating sockets and finding them should retrieve it"

    (doseq [id (get-test-socket-ids)          ]

      (is (not-empty (websocket-clients/get-websocket id)))

      )

    ))

(deftest test-socket-status-update
  (testing "creating sockets and updating it"


    (let [
          socket-id (get-a-test-socket-id)
          socket (websocket-clients/get-websocket socket-id)
          ]

      (is (not-empty  socket))
      (is (= (:waiting websocket-clients/STATUS ) (:status socket)))

      ;update socket

      (websocket-clients/update-websocket-status-to-log-in! socket-id)
      (is (= (:loggedIn websocket-clients/STATUS ) (:status (websocket-clients/get-websocket socket-id))))


      )

    ))


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


(deftest test-creating-sockets-for-valid-session
  (testing "creating sockets for only valid sessions"


    (let [sockets (websocket-clients/get-all-websockets)]

      (info (.size sockets))

      (is (= 2  (.size sockets)))



      )))


