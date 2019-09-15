(ns server.db.db
  (:require [clojure.java.jdbc :as sql]
	    [clojure.data.json :as json]))

(def database-settings ((keyword (System/getenv "NODE_ENV")) (json/read-str (slurp "./db/config/config.json") :key-fn keyword))) 

(def database {:dbtype "mysql"
               :subname (str (:host database-settings) ":3306")
               :dbname (:database database-settings)
               :user (:username database-settings)
               :password (:password database-settings)})

(defn get-user-from-db-for [email]
  (let [user (first (sql/query database ["select * from Users where email = ?" email]))]
    (if (nil? user)
      nil
      {:id (:id user)
       :username (:username user)
       :email (:email user)
       :password (:password user)
       :createdat (:createdat user)
       :updatedat (:updatedat user)})
    user))

(defn save-user-to-db [email password]
  (let [user (first (sql/insert! database :Users {:email email :password password}))]
    user))
