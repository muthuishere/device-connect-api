(ns send-phone-api.api.session (:require
                                 [clojure.data.json :refer (write-str)]
                                 [clojure.tools.logging :refer [info]]
                                 [send-phone-api.socket.handler :refer [send-to-client-socket socket-exists]]
                                 [send-phone-api.socket.worker :refer [put-to-channel]]
                                 [send-phone-api.socket.sockets :as websockets]
                                 [send-phone-api.sessions :as sessions]
                                 [send-phone-api.http_responses :refer [success-json success-string bad-request success-raw]]
                                 ))




(defn create-session [req]
  (info req)

  (let [response (sessions/create-session)]
    (sessions/add-to-global-session response)
    (info response)
    (success-json response)
    ))





(defn get-all-sessions [req]
  (success-raw (sessions/get-all-sessions))
  )



