(ns send-phone-api.api-handlers-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :refer [info]]
            [send-phone-api.api.api-handlers :refer [create-session get-all-sessions send-to-websocket]]
            [send-phone-api.socket.websocket-handlers :refer [socket-handler send-to]]
            [send-phone-api.mocks :as mock-api]
            [send-phone-api.mocks :refer [create-socket create-sockets!]]
            [send-phone-api.socket.worker :refer [init-socket-channel]]
            [send-phone-api.socket.websocket-clients :refer [add-websocket! get-all-websockets] ]
            [ring.mock.request :as mock]
            [send-phone-api.core :refer :all]))



(defn mock-request []

  (mock/request :get "/doc/10")

  )

(deftest verify-create-session
  (testing "creating session should get session id"
    (let [mockRequest (mock-request)
          value (create-session mockRequest)]

      (is (not-empty value))
    )))






(deftest test-send-to-websocket-with-valid-should-send-message
  (testing "Sending to client with existing channel should send data"

   (create-sockets!)
   (with-redefs [send-to (fn [socket msg] {})]
   (let [ response  (send-to-websocket {:id "21" :message "hello"})]

     (info response)
     (is (.contains (:body response)  "Invalid Client id"))



    ))))

(deftest sending-client-with-invalid
  (testing "Sending to client with invalid id should throw error"

   (create-sockets!)
   (with-redefs [send-to (fn [socket msg]
                           (info "dummy" msg)
                           {})]
     (let [ response  (send-to-websocket {:id "21" :message "hello"})]

     (info response)
     (is (.contains (:body response)  "Invalid Client id"))
     (is (= (:status response)  400))



    ))))


(deftest test-current
  (testing "Sending client with valid id should send data"

   (create-sockets!)
   (init-socket-channel)
   (with-redefs [send-to (fn [socket msg]
                           (info "dummy" msg)
                           {})]
     (let [ response  (send-to-websocket {:id "ar" :message "hello"})]
       (info response)


     (is (.contains (:body response)  "message sent"))
     (is (= (:status response)  200))
       (Thread/sleep 5000)


    ))))


