(ns send-phone-api.api.session-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :refer [info]]
            [send-phone-api.api.session :refer :all]
            [send-phone-api.mocks.shared :refer :all]
            ))


(deftest verify-create-session
  (testing "creating session should get session id"
    (let [mockRequest (mock-request)
          value (create-session mockRequest)]

      (is (not-empty value))
      )))


