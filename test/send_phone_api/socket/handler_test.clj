(ns send-phone-api.socket.handler-test
  (:require [clojure.test :refer :all]
            [send-phone-api.mocks.shared :refer :all]
            [send-phone-api.socket.handler :as handlers]
            [send-phone-api.socket.sockets :as sockets]
            [send-phone-api.sessions :as sessions]
            [send-phone-api.socket.wrapper :as socket-wrapper]
            [clojure.tools.logging :refer [info]]
            ))



(defn test-setup [f]

  (with-redefs [socket-wrapper/send-to-channel (fn [socket msg] (info "sending to channel " msg) {})]
    (with-redefs [socket-wrapper/open-channel (fn [req info] (info "opening dummy channel " info) {})]
      (f)
      )))

; Here we register my-test-fixture to be called once, wrapping ALL tests
; in the namespace
(use-fixtures :once test-setup)


(deftest test-opening-sockets-with-valid-session
  (testing "opening sockets with valid session should open socket"
    (let [id (create-test-session)]

      (is (not-empty (sessions/get-session id)))
      (is (empty (sockets/get-websocket id)))

      (handlers/open-socket id (get-a-mock-request))

      (is (empty (sessions/get-session id)))
      (is (not-empty (sockets/get-websocket id)))

      )

    ))