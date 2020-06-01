(ns send-phone-api.worker-test
  (:require
    [clojure.test :refer :all]

    [send-phone-api.sessions :as sessions]))


(deftest init-session
  (testing "creating session should get session id"
    (let [ value (sessions/create-session)]

      (is (not-empty value))
      )))