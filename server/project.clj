 (defproject server "0.1.0-SNAPSHOT"
   :description "Coffeepot Server"
   :dependencies [[metosin/compojure-api "2.0.0-alpha30"]
                  [mysql/mysql-connector-java "5.1.18"]
                  [org.clojure/clojure "1.10.0"]
 		  [org.clojure/data.json "0.2.6"]
                  [org.clojure/java.jdbc "0.7.10"]
                  [ring-cors "0.1.13"]
                  [ring/ring-defaults "0.3.2"]]
   :ring {:handler server.handler/app}
   :uberjar-name "server.jar"
   :profiles {:dev {:repositories [["GCS Maven Central mirror EU" "https://maven-central-eu.storage-download.googleapis.com/repos/central/data/"]]
                    :dependencies [[javax.servlet/javax.servlet-api "3.1.0"]]
                    :plugins [[lein-ring "0.12.5"]]}})
