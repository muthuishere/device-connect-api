(ns send-phone-api.sessions)


(def all-sessions (atom []))


(defn add-to-global-session [response]
  (swap! all-sessions conj response   )
  )

(defn get-all-sessions []
  @all-sessions
  )


(defn uuid []
  (.toString (java.util.UUID/randomUUID))
  )

(defn create-session []

  {:sessionId (uuid)  :time (System/currentTimeMillis)}
  )


