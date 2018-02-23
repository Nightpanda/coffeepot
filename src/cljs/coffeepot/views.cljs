(ns coffeepot.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [coffeepot.subs :as subs]
            [coffeepot.events :as events]))

(defn provider []
  (new js/firebase.auth.GoogleAuthProvider))

(defn google-sign-in []
  (println "loggin in")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
    (.signInWithPopup (.auth @firebase-app) (provider))))

(defn logout-auth []
  (println "logging out")
    (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
    (.signOut (.auth @firebase-app))))

(defn login-button []
  [re-com/button
   :label "Login with Google"
   :on-click (fn login-click [e]
        (google-sign-in))
   ])

(defn logout-button []
  [re-com/button
   :label "Logout"
   :on-click (fn logout-click [e]
        (logout-auth))
   ])

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label @name
     :level :level1]))

(defn add-auth-listener []
  (println "Adding auth listener!")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        listener-alive? (re-frame/subscribe [::subs/auth-listener])]
    (if (and (not @listener-alive?) (some? @firebase-app))
      (do
        (re-frame/dispatch [::events/change-auth-listener-status true])
        (.onAuthStateChanged
         (.auth @firebase-app)
         (fn auth-state-changed [user-obj]
           (if (nil? user-obj)
             (println "logged out!")
             (println "logged in! Hi")))
         (fn auth-error [error]
           (js/console.log error))))
      (println "Firebase app not present or listener already active!")))
  )

(defn main-panel []
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        listener-alive? (re-frame/subscribe [::subs/auth-listener])]
    (fn []
      (if (false? @listener-alive?)
        (add-auth-listener))
      (if (some? @firebase-app)
        (do
          [re-com/v-box
           :height "100%"
           :children [[title]
                      [login-button]
                      [logout-button]

]])))))
