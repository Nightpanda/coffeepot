(ns coffeepot.firebase
  (:require [re-frame.core :as re-frame]
            [coffeepot.subs :as subs]
            [coffeepot.envconfig :as env]
            [reagent.core :as r]
            [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
            [coffeepot.events :as events]))

(def firebase-config
   #js {:apiKey (:apiKey env/firebase)
        :authDomain (:authDomain env/firebase)
        :databaseURL (:databaseURL env/firebase)})

(defn initialize-firebase []
  (.log js/console firebase-config)
  (re-frame/dispatch [::events/firebase (js/firebase.initializeApp firebase-config)]))

(def providers
  {:google (new js/firebase.auth.GoogleAuthProvider)})

(defn signInWithProvider [provider]
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
    (.signInWithPopup (.auth @firebase-app) (provider providers))))

(defn logout-auth []
  (debug "logging out")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
    (.signOut (.auth @firebase-app))
    (re-frame/dispatch [::events/sub-view ""])
    (re-frame/dispatch [::events/user-uid nil])
    (re-frame/dispatch [::events/username nil])
    (re-frame/dispatch [::events/user-description nil])))

(defn db-ref [path]
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        fire-db (.database @firebase-app)]
    (.ref fire-db (str "/" path))))

(defn get-username! [uid]
    (.once (db-ref (str "users/" uid "/username"))
      "value"
      (fn received-db [snapshot]
        (re-frame/dispatch [::events/username (.val snapshot)]))))

(defn get-user-description! [uid]
  (.once (db-ref (str "users/" uid "/description"))
    "value"
    (fn received-db [snapshot]
      (re-frame/dispatch [::events/user-description (.val snapshot)]))))

(defn get-username-current-user! []
  (let [uid (re-frame/subscribe [::subs/user-uid])]
    (.once (db-ref (str "users/" @uid "/username"))
           "value"
           (fn received-db [snapshot]
             (re-frame/dispatch [::events/username (.val snapshot)])))))

(defn save-username-uid! [uid username]
 (.set (db-ref (str "users/" uid "/username")) username)
 (.set (db-ref (str "usernames/" username)) #js {:created (js/Date.now)})
  (re-frame/dispatch [::events/username username]))

(defn save-user-description! [uid description]
  (.set (db-ref (str "users/" uid "/description")) description)
  (re-frame/dispatch [::events/user-description description]))



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

(defn add-auth-listener []
  (debug "Adding auth listener!")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        listener-alive? (re-frame/subscribe [::subs/auth-listener])]
    (if (and (not @listener-alive?) (some? @firebase-app))
      (do
        (re-frame/dispatch [::events/change-auth-listener-status true])
        (.onAuthStateChanged
         (.auth @firebase-app)
         (fn auth-state-changed [user-obj]
           (if (nil? user-obj)
            (do
              (re-frame/dispatch [::events/user-uid nil])
              (re-frame/dispatch [::events/current-view :logged-out]))
            (do
              (re-frame/dispatch [::events/user-uid (.-uid user-obj)])
              (re-frame/dispatch [::events/current-view :logged-in])
              (get-username! (.-uid user-obj))
              (get-user-description! (.-uid user-obj)))))
         (fn auth-error [error]
           (debug error))))
      (debug "Firebase app not present or listener already active!"))))
