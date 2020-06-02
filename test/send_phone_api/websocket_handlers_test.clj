(ns send-phone-api.websocket-handlers-test
  (:require
    [clojure.test :refer :all]
    [send-phone-api.socket.websocket-clients :as websocket-clients]
    [ring.mock.request :as mock]
    [clojure.tools.logging :refer [info]]
    [send-phone-api.websocket-handlers :refer :all]
    [send-phone-api.mocks :refer :all]
    ))


(deftest test-current
  (testing "creating sockets for only valid sessions"

    (create-sockets!)
    (let [sockets (websocket-clients/get-all-websockets)]



      (is (= 2  (.size sockets)))



      )))