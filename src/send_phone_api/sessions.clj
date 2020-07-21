(ns send-phone-api.sessions)


(def all-sessions (atom {}))


(defn add-to-global-session [response]

  (let [id (:sessionId response)]
    (swap! all-sessions assoc id response))

  )

(defn get-all-sessions []
  @all-sessions
  )



(defn uuid []
  ;Generate unique and allow validation
  (.toString (java.util.UUID/randomUUID))
  )

(defn get-session [id]

  (get-in (get-all-sessions) [id])

  )

(defn create-session []

  ;{:sessionId "2021"   :time (System/currentTimeMillis)}
  {:sessionId (uuid)  :time (System/currentTimeMillis)}
  )

(defn remove-session [id]

  ;Another find a better way
  ;(reset! all-sessions (filter #(not= id (:sessionId %)) (get-all-sessions )))
  (swap! all-sessions dissoc [id] )
  )


