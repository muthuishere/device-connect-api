(ns send-phone-api.api-handlers-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :refer [info]]
            [send-phone-api.api.api-handlers :refer [create-session get-all-sessions send-to-websocket]]
            [send-phone-api.socket.websocket-handlers :refer [socket-handler send-to]]
            [send-phone-api.socket.websocket-clients :refer [add-websocket! get-all-websockets] ]
            [ring.mock.request :as mock]
            [send-phone-api.core :refer :all]))


(defn create-channel [name]

  {:send! (fn [req]
            (info "sending " name)
            )}
  )

(defn mock-request []

  (mock/request :get "/doc/10")

  )

(deftest verify-create-session
  (testing "creating session should get session id"
    (let [mockRequest (mock-request)
          value (create-session mockRequest)]
      (info value)
      (info (get-all-sessions mockRequest))
      (is (not-empty value))
    )))



(defn create-channels! []

  (add-websocket! "ar" (create-channel "ar"))
  (add-websocket! "al" (create-channel "al"))

  )

(deftest test-creating-channels
  (testing "creating channels to update clients"

   (create-channels!)
    (let [clients (get-all-websockets)]
      (info (.size clients))

      (is (= 2  (.size clients)))



    )))


(deftest test-send-to-websocket-with-valid-should-send-message
  (testing "Sending to client with existing channel should send data"

   (create-channels!)
   (with-redefs [send-to (fn [socket msg] {})]
   (let [ response  (send-to-websocket "ar" (mock-request))]

     (info response)
     (is (.contains (:body response)  "message sent"))



    ))))

(deftest test-current
  (testing "Sending to client with invalid id should throw error"

   (create-channels!)
   (with-redefs [send-to (fn [socket msg] {})]
   (let [ response  (send-to-websocket "invalid" (mock-request))]

     (info response)
     (is (.contains (:body response)  "Invalid Client id"))
     (is (= (:status response)  400))



    ))))


