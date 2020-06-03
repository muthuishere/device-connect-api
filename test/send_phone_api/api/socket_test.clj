(ns send-phone-api.api.socket-test
  (:require [clojure.test :refer :all]
            [send-phone-api.api.socket :refer :all]
            [send-phone-api.mocks.shared :refer :all]
            [clojure.tools.logging :refer [info]]
            [send-phone-api.socket.websocket-handlers :as websocket-handlers]
            [send-phone-api.socket.worker :as worker]
            ))



(defmacro  with-mock-send-to []

   [websocket-handlers/send-to (fn [socket msg] {})]
  )

(deftest test-send-to-websocket-with-valid-should-send-message
  (testing "Sending to client with existing channel should send data"

    (create-test-sockets!)
    (with-redefs (with-mock-send-to)
      (let [ response  (send-to-websocket {:id "21" :message "hello"})]

        (info response)
        (is (.contains (:body response)  "Invalid Client id"))



        ))))


(deftest sending-to-websocket-with-invalid
  (testing "Sending to client with invalid id should throw error"

    (create-test-sockets!)
    (with-redefs (with-mock-send-to)
      (let [ response  (send-to-websocket {:id "21" :message "hello"})]

        (info response)
        (is (.contains (:body response)  "Invalid Client id"))
        (is (= (:status response)  400))



        ))))



(deftest send-to-websocket-should-receive-data
  (testing "Sending client with valid id should send data"

    (create-test-sockets!)
    (worker/init-socket-channel)
    (with-redefs (with-mock-send-to)
      (let [ response  (send-to-websocket {:id "ar" :message "hello"})]
        (info response)


        (is (.contains (:body response)  "message sent"))
        (is (= (:status response)  200))
        (Thread/sleep 5000)


        ))))
