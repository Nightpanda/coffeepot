(ns coffeepot.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [coffeepot.subs :as subs]
            ))

(defn init []
 (js/firebase.initializeApp
   #js {:apiKey "AIzaSyABjP8bTTvEfy1n_pgR8oiqFBCn6hr4CP8"
        :authDomain "coffeepot-d13ea.firebaseapp.com"
        :databaseURL "https://coffeepot-d13ea.firebaseio.com"
        :storageBucket "coffeepot-d13ea.appspot.com"}))

(.onAuthStateChanged
 (js/firebase.auth)
 (fn auth-state-changed [user-obj]
  (if (nil? user-obj)
    (println "logged out!")
    (println "logged in!")))
 (fn auth-error [error]
   (js/console.log error)))

(defn provider []
  (new js/firebase.auth.GoogleAuthProvider))

(defn google-sign-in []
  (.signInWithPopup (.auth js/firebase) (provider)))

(defn logout-auth []
  (.signOut (.auth js/firebase)))

(defn login-button []
  [re-com/button
   :label "Login with Google"
   :on-click (fn login-click [e]
        (google-sign-in))
   ])

(defn logout-button []
  [re-com/button
   :label "Logout"
   :on-click (fn login-click [e]
        (logout-auth))
   ]) 

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label @name
     :level :level1]))

(defn main-panel []
  [re-com/v-box
   :height "100%"
   :children [[title]
              [login-button]
              [logout-button]]])

(init)