(ns coffeepot.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :as async]
            [re-frame.core :as re-frame]
            [coffeepot.events :as events]))

(def base-url "http://localhost:3000/api/users/authenticate")

(defn authenticate-user [email password]
  (go (let [response (:body (async/<! (http/post base-url
                                                 {:with-credentials? false
                                                  :query-params {"email" email
                                                                 "password" password}})))]
        (re-frame/dispatch [::events/username (:username response)])
        (re-frame/dispatch [::events/user-uid (:username response)]))))
