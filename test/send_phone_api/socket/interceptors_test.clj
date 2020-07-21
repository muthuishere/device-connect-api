(ns send-phone-api.socket.interceptors-test
  (:require [clojure.test :refer :all]

            [send-phone-api.socket.sockets :as websocket-clients]

            [send-phone-api.mocks.shared :refer :all]
            [clojure.tools.logging :refer [info]]

            ))

(deftest calling-login-action-should-trigger-login-interceptor
  (testing "calling-login-action-should-trigger-login-interceptor"

    (info "hi")
  ))