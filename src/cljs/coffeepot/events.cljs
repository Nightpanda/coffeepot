(ns coffeepot.events
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r]
            [coffeepot.db :as db]
            [coffeepot.subs :as subs]))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::change-auth-listener-status
 (fn [db  [_ status]]
   (assoc db :auth-listener status)))

(re-frame/reg-event-db
 ::user-logged-in
 (fn [db  [_ logged-in]]
   (assoc-in db [:user :logged-in] logged-in)))

(re-frame/reg-event-db
 ::change-locale
 (fn [db  [_ locale]]
   (assoc db :locale locale)))

(def firebase-config
   #js {:apiKey "AIzaSyABjP8bTTvEfy1n_pgR8oiqFBCn6hr4CP8"
        :authDomain "coffeepot-d13ea.firebaseapp.com"
        :databaseURL "https://coffeepot-d13ea.firebaseio.com"})

(re-frame/reg-event-db
 ::initialize-firebase
 (fn [db [_]]
   (assoc db :firebase-app  (js/firebase.initializeApp firebase-config))))

(re-frame/reg-event-db
  ::current-view
  (fn [db  [_ current-view]]
    (assoc db :current-view current-view)))

(re-frame/reg-event-db
  ::sub-view
  (fn [db  [_ sub-view]]
    (assoc db :sub-view sub-view)))

(defn db-ref [path] 
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        fire-db (.database @firebase-app)]
    (.ref fire-db (str "/" path))))

(re-frame/reg-event-db
 ::user-uid
 (fn [db [_ uid]]
   (assoc db :user-uid uid)))

(re-frame/reg-event-db
 ::username
 (fn [db [_ username]]
   (assoc db :username username)))

(re-frame/reg-event-db
  ::user-description
  (fn [db [_ description]]
    (assoc db :user-description description)))

(defn get-username! [uid]
    (.once (db-ref (str "users/" uid "/username"))
      "value"
      (fn received-db [snapshot]
        (re-frame/dispatch [::username (.val snapshot)]))))

(defn get-user-description! [uid]
  (.once (db-ref (str "users/" uid "/description"))
    "value"
    (fn received-db [snapshot]
      (re-frame/dispatch [::user-description (.val snapshot)]))))

(defn save-username-uid [uid username]
 (.set (db-ref (str "users/" uid "/username")) username)
 (.set (db-ref (str "usernames/" username)) #js {:created (js/Date.now)})
  (re-frame/dispatch [::username username]))

(defn save-user-description [uid description]
  (.set (db-ref (str "users/" uid "/description")) description)
  (re-frame/dispatch [::user-description description]))

(re-frame/reg-event-db
  ::get-username!
  (fn [db [_]]
    (let [uid (re-frame/subscribe [::subs/user-uid])]
      (.once (db-ref (str "users/" @uid "/username"))
        "value"
        (fn received-db [snapshot]
          (assoc db :username (.val snapshot)))))))

(defn listen-firebase-db [path f]
  (let [ref (db-ref path)
        a (r/atom nil)]
    (.on ref "value" (fn [x]
                        (reset! a (.val x))))
    (r/create-class
      {:display-name "listener"
        :component-will-unmount
        (fn will-unmount-listener [this]
          (.off ref))
        :reagent-render
        (fn render-listener [args]
          (into [f a] args))})))