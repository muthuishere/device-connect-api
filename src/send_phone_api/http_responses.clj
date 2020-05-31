(ns send-phone-api.http_responses
  (:require
    [clojure.data.json :refer (write-str)]
    ))


(defn success-json [msg]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body    (write-str msg)})

(defn success-string [msg]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body    (write-str {:msg msg})})

(defn bad-request [msg]
  {:status  400
   :headers {"Content-Type" "text/json"}
   :body    msg})