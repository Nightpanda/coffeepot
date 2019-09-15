(ns server.handler
  (:require [compojure.api.sweet :refer [api context POST]]
            [ring.middleware.cors :as cors]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.util.http-response :refer [ok]]
            [schema.core :as s]
            [server.db.db :as db]))

(s/defschema User
  {:id s/Int
   :username s/Str
   :email s/Str
   :password s/Str
   :createdat java.sql.Timestamp
   :updatedat java.sql.Timestamp})

(def no-user
  {:id 0
   :username ""
   :email ""
   :password ""
   :createdAt ""
   :updatedAt ""})

(defn get-user-for [email]
  (db/get-user-from-db-for email))

(defn authenticate-user [email password]
  (let [user (get-user-for email)
        password-correct? (= (:password user) password)]
    (if password-correct?
      user
      no-user)))

(def app-routes
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Coffeepot Server"
                    :description "Handles database for Coffeepot"}
             :tags [{:name "api", :description "APIs for coffeepot"}
                    {:name "users", :description "APIs for users"}]}}}

    (context "/api" []
      :tags ["api"]

      (context "/users" []
               :tags ["users"]

               (POST "/authenticate" []
                     :return User
                     :query-params [email :- s/Str, password :- s/Str]
                     :summary "Authenticates user or something."
                     (ok (authenticate-user email password)))
               (POST "/user" []
                     :return s/Any
                     :query-params [email :- s/Str, password :- s/Str]
                     :summary "Create new user."
                     (ok (db/save-user-to-db email password)))
))))

(def app
  (-> app-routes
      (wrap-defaults api-defaults)
      (cors/wrap-cors :access-control-allow-credentials "true"
                      :access-control-allow-origin [#".*"]
                      :access-control-allow-methods [:get :put :post :delete])))
