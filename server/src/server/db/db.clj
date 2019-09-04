(ns server.db.db
  (:require [clojure.java.jdbc :as sql]))

(def database {:dbtype "mysql"
               :subname "//127.0.0.1:3306"
               :dbname "coffeepot_dev"
               :user "root"
               :password "root"})

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
