(ns coffeepot.events
  (:require [re-frame.core :as re-frame]
            [coffeepot.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db
   ))

(re-frame/reg-event-db
 ::change-auth-listener-status
 (fn [db  [_ status]]
   (assoc db :auth-listener status)
   ))

(def firebase-config
   #js {:apiKey "AIzaSyABjP8bTTvEfy1n_pgR8oiqFBCn6hr4CP8"
        :authDomain "coffeepot-d13ea.firebaseapp.com"
        :databaseURL "https://coffeepot-d13ea.firebaseio.com"})

(re-frame/reg-event-db
 ::initialize-firebase
 (fn [db [_]]
   (assoc db :firebase-app  (js/firebase.initializeApp firebase-config))))
