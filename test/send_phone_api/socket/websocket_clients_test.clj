(ns send-phone-api.socket.websocket-clients-test
  (:require [clojure.test :refer :all]
            [send-phone-api.socket.websocket-clients :as websocket-clients]
            [send-phone-api.mocks.shared :refer :all]

            [clojure.tools.logging :refer [info]]
            ))




(deftest test-creating-sockets
  (testing "creating sockets should update web-socket-clients"

    (create-test-sockets!)
    (let [sockets (websocket-clients/get-all-websockets)]



      (is (= 2  (.size sockets)))



      )))

(deftest test-find-socket
  (testing "creating sockets and finding them should retrieve it"
    (create-test-sockets!)
    (doseq [id (get-test-socket-ids)          ]

      (is (not-empty  (websocket-clients/get_websocket id )))

      )

    ))

(deftest test-socket-status-update
  (testing "creating sockets and updating it"
    (create-test-sockets!)

    (let [
          socket-id (get-a-test-socket-id)
          socket (websocket-clients/get_websocket socket-id )
          ]

      (is (not-empty  socket))
      (is (= (:waiting websocket-clients/STATUS ) (:status socket)))

      ;update socket

      (websocket-clients/update-websocket-status-to-log-in! socket-id)
      (is (= (:loggedIn websocket-clients/STATUS ) (:status (websocket-clients/get_websocket socket-id ))))


      )

    ))


(deftest test-create-sockets-and-remove
  (testing "creating sockets and removing it"
    (create-test-sockets!)

    (let [
          socket-id (get-a-test-socket-id)
          socket (websocket-clients/get_websocket socket-id )
          ]

      (is (not-empty  socket))

      ;remove socket

      (websocket-clients/remove-websocket! socket-id)


      (is (empty  (websocket-clients/get_websocket socket-id )))
      )

    ))

(deftest test-creating-sockets-for-valid-session
  (testing "creating sockets for only valid sessions"

    (create-test-sockets!)
    (let [sockets (websocket-clients/get-all-websockets)]



      (is (= 2  (.size sockets)))



      )))


