(ns send-phone-api.sessions-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :refer [info]]
            [send-phone-api.sessions :as sessions]
            [ring.mock.request :as mock]))



(defn mock-request []

  (mock/request :get "/doc/10")

  )

(deftest verify-create-session
  (testing "creating session should get session id"
    (let [ value (sessions/create-session)]

      (is (not-empty value))
      )))

(defn create-session-and-return []
  (let [session (sessions/create-session)]
    (sessions/add-to-global-session session)
     session
    )


  )
(deftest verify-remove-session
  (testing "removing session should remove from list of session"


    (let [
          session-1 (create-session-and-return)
          session-2 (create-session-and-return)
          session-3 (create-session-and-return)


          sessionId (:sessionId session-1)]

    (info (sessions/get-session sessionId))

      (is (not-empty (sessions/get-session sessionId)))

      (sessions/remove-session sessionId)

      (is (empty (sessions/get-session sessionId)))


      )))


(deftest test-remove-session-on-non-existent-session
  (testing "removing session if not exists should remove it"


    (let [session-1 (create-session-and-return)
          sessionId (:sessionId session-1)
          ]

      (sessions/remove-session "some-dummy")


      (is (not-empty (sessions/get-session sessionId)))

      )))
