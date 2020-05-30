(ns send-phone-api.api-handlers-test
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :refer [info]]
            [send-phone-api.api-handlers :refer [create-session get-all-sessions]]
            [send-phone-api.websocket-handlers :refer [socket-handler test-socket-handler get-all-active-sockets]]
            [ring.mock.request :as mock]
            [send-phone-api.core :refer :all]))

(deftest verify-create-session
  (testing "creating session should get session id"
    (let [value (create-session (mock/request :get "/doc/10") )]
      (info value)
      (info (get-all-sessions))
      (is (not-empty value))
      (is (= 1  (.length (get-all-sessions))))
    )))

(deftest verify-sockets
  (testing "creating sockets"
    ;(socket-handler "ar" (mock/request :get "/doc/10" :on-receive ))
    ;(socket-handler "arl" (mock/request :get "/doc/11" :on-receive))
    (test-socket-handler "ar" {:hi "halo"})
    (test-socket-handler "arl" {:hi "halo"})

    (let [clients (get-all-active-sockets)]
      (info clients)

      (doseq [client (vals  clients)]
        ;; send all, client will filter them
        (info client)

       (info (:id client) )
       (info (:channel client) )

        )

    )))