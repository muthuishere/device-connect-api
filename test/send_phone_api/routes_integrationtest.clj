(ns send-phone-api.routes-integrationtest
  (:require [clojure.test :refer :all]
            [clojure.tools.logging :refer [info]]
            [clojure.data.json :as json]
            [ring.middleware.defaults :refer :all]
            [send-phone-api.routes :refer :all]
            [ring.mock.request :as mock]))

;(in-ns 'send-phone-api.routes-integrationtest)
;(load "send-phone-api/routes-integrationtest")
;(def response (app-routes (mock/request :get "/register")))
;(require '[ring.mock.request :as mock])
;(require ' [clojure.data.json :as json])
;(require ' [ring.middleware.defaults :refer :all])
;(def response (handler (mock/request :get "/register")))
;( def handler ( wrap-defaults #'app-routes api-defaults))
;(:body response)
;(json/read-str (:body response)  :key-fn keyword)
;(:sessionId (json/read-str (:body response)  :key-fn keyword))

(deftest register-test

  (let [response (app-routes (mock/request :get "/register"))]
    (is (= 200 (:status response)))
    (is (= {"Content-Type" "text/json"} (:headers response)))
    )
  )


(defn get-session-id []

  (let [response (app-routes (mock/request :get "/register"))]
    (:sessionId (json/read-str (:body response) :key-fn keyword))
    )
  )




(deftest register-websocket-test

  (let [session-id (get-session-id)]


    (info session-id)
    (is (= 1 1))

    )
  )