(ns coffeepot.app
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [coffeepot.subs :as subs]
            [coffeepot.events :as events]
            [coffeepot.components.common :as c]
            [coffeepot.views.coffee.core :as coffee]))

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
             (re-frame/dispatch [::events/user-logged-in false])
             (re-frame/dispatch [::events/user-logged-in true])))
         (fn auth-error [error]
           (js/console.log error))))
      (println "Firebase app not present or listener already active!"))))

(defn login-page []
  [re-com/v-box
   :min-height "100vh"
   :children [[c/header
               [c/header-section
                [c/header-item
                 [c/header-brand-item
                  [:img {:src "images/weblogod.png" :height "80px" :width "80px"}]]]
                [c/header-item
                 [c/header-brand-item
                  [c/title "Coffeepot"]]]]
               [c/header-section
                [c/header-item
                 [c/button "Login with Google" (fn login-click [e]
                                                 (google-sign-in))]]]]
              [c/content
               [c/phone-preview-box
                "1" "images/coffeepotphone.png"]
               [c/title-box
                "2" "CoffeePot"]]
              [c/footer "© 2018 - Brewed with love by CoffeePot Inc."]]])

(defn main-panel []
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        listener-alive? (re-frame/subscribe [::subs/auth-listener])
        name (re-frame/subscribe [::subs/name])
        user (re-frame/subscribe [::subs/user])]
    (fn []
      (if (false? @listener-alive?)
        (add-auth-listener))
      (if (some? @firebase-app)
        (if (:logged-in @user)
          [coffee/app-main]
          [login-page])))))
