(defproject send-phone-api "0.1.0-SNAPSHOT"
  :description "API for send-phone app and websocket for send-phone-browser"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :repositories [["jitpack" "https://jitpack.io"]
                 ["central" "https://repo1.maven.org/maven2"]
                 ["clojure" "https://build.clojure.org/releases"]
                 ["clojars" "https://clojars.org/repo"]
                 ["java.net" "https://download.java.net/maven/2"]
                 ["jboss.release" "https://repository.jboss.org/nexus/content/groups/public"]
                 ["terracotta-releases" "https://www.terracotta.org/download/reflector/releases"]
                 ["terracotta-snapshots" "https://www.terracotta.org/download/reflector/snapshots"]
                 ["apache.snapshots" "https://repository.apache.org/snapshots"]]
  :dependencies [
                 [org.clojure/clojure "1.10.0"]
                 [org.clojure/core.async "1.2.603"]
                 [org.clojure/tools.namespace "1.0.0"]
                 [ring/ring-devel "1.8.1"]
                 [compojure "1.6.1"]

                 ; Our Http library for client/server
                 [http-kit "2.3.0"]
                 ; Ring defaults - for query params etc
                 [ring/ring-defaults "0.3.2"]
                 ; Clojure data.JSON library
                 [org.clojure/data.json "1.0.0"]
                 [org.clojure/tools.logging "0.2.3"]
                 ]

  :repl-options {

                 :init-ns send-phone-api.routes
                 }

                 :plugins [[lein-auto "0.1.3"]
            [lein-cloverage "1.1.2"]]
  :main ^:skip-aot send-phone-api.core
  :target-path "target/%s"



  :profiles {:dev {
                   :main send-phone-api.core/-dev-main
                   :dependencies [
                                  [ring/ring-mock "0.4.0"]


                                  ]}
             :uberjar {:aot :all}})
