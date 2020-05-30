(ns send-phone-api.core
   (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
             [clojure.tools.logging :refer [info]]
            [compojure.route :as route]
             [clojure.data.json :refer [json-str read-json]]
            [ring.middleware.defaults :refer :all]
            [clojure.pprint :as pp]
            [clojure.string :as str]
             [send-phone-api.api-handlers :refer [create-session get-all-clients send-to-client ]]
             [send-phone-api.websocket-handlers :refer [socket-handler]]
            [clojure.data.json :as json])
  (:gen-class))



(defn- now [] (quot (System/currentTimeMillis) 1000))

(def clients (atom {}))                 ; a hub, a map of client => sequence number

(let [max-id (atom 0)]
  (defn next-id []
    (swap! max-id inc)))

(defonce all-msgs (ref [{:id (next-id),            ; all message, in a list
                         :time (now)
                         :msg "this is a live chatroom, have fun",
                         :author "system"}]))


; Simple Body Page
(defn simple-body-page [req] ;(3)
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "Hello World"})
;
; request-example
(defn request-example [req]
     {:status  200
      :headers {"Content-Type" "text/html"}
      :body    (->
                (pp/pprint req)
                (str "Request Object: " req))})

; Hello-name handler
(defn hello-name [req]
     {:status  200
      :headers {"Content-Type" "text/html"}
      :body    (->
                (pp/pprint req)
                (str "Hello " (:name (:params req))))})

; my people-collection mutable collection vector
(def people-collection (atom []))

;Collection Helper functions to add a new person
(defn addperson [firstname surname]
  (swap! people-collection conj {:firstname (str/capitalize firstname)
                                     :surname (str/capitalize surname)}))

; Example JSON objects
(addperson "Functional" "Human")
(addperson "Micky" "Mouse")

; Return List of People
(defn people-handler [req]
        {:status  200
         :headers {"Content-Type" "text/json"}
         :body    (str (json/write-str @people-collection))})

; Helper to get the parameter specified by pname from :params object in req
(defn getparameter [req pname] (get (:params req) pname))

; Add a new person into the people-collection
(defn addperson-handler [req]
        {:status  200
         :headers {"Content-Type" "text/json"}
         :body    (-> (let [p (partial getparameter req)]
                        (str (json/write-str (addperson (p :firstname) (p :surname))))))})


(defn mesg-received [msg]
  (let [data (read-json msg)]
    (info "mesg received" data)

    (when (:msg data)
      (let [data (merge data {:time (now) :id (next-id)})]
        (dosync
          (let [all-msgs* (conj @all-msgs data)
                total (count all-msgs*)]
            (if (> total 100)
              (ref-set all-msgs (vec (drop (- total 100) all-msgs*)))
              (ref-set all-msgs all-msgs*))))))
    (doseq [client (keys @clients)]
      ;; send all, client will filter them
      (server/send! client (json-str @all-msgs)))))


(defn chat-handler [req]
  (server/with-channel req channel
                (info channel "connected")
                (swap! clients assoc channel true)
                (server/on-receive channel #'mesg-received)
                (server/on-close channel (fn [status]
                                    (swap! clients dissoc channel)
                                    (info channel "closed, status" status)))))


; Our main routes
(defroutes app-routes
  (GET "/websocket/:id" [id] (fn [req]
                               (socket-handler id req)
                               ))

           (GET "/send-to-client/:id" [id] (fn [req]
                               (send-to-client id req)
                               ))

  (GET "/createsession" []  create-session)
  (GET "/get-all-clients" []  get-all-clients)

  (route/not-found "Error, page not found!"))

; Our main entry function
(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ; Run the server with Ring.defaults middleware
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    ; Run the server without ring defaults
    ;(server/run-server #'app-routes {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
