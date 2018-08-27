(ns coffeepot.views.signin.core
    (:require [re-frame.core :as re-frame]
              [reagent.core :as r]
              [re-com.core :as re-com]
              [cljsjs.material-ui]
              [cljs-react-material-ui.reagent :as ui]
              [cljs-react-material-ui.icons :as ic]
              [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
              [coffeepot.components.common :as c]
              [coffeepot.components.navheader :as nav]
              [coffeepot.events :as events]
              [coffeepot.subs :as subs]
              [coffeepot.localization :refer [localize localize-with-substitute]]
              [coffeepot.components.styles :as styles]
              [coffeepot.firebase :as firebase]))

(defn google-provider []
  (new js/firebase.auth.GoogleAuthProvider))

(defn email-sign-in []
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
    (.signInWithEmailAndPassword (.auth @firebase-app) "nakki@noemail.com" "nakkitest")))
              
(defn google-sign-in []
  (debug "loggin in")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
    (.signInWithPopup (.auth @firebase-app) (google-provider))))

(def sign-in-selection
    (fn []
      [ui/card
       [ui/card-text
        [ui/raised-button 
          {:label (localize-with-substitute :login (localize :google))
           :id "google-sign-in"
           :on-click (fn login-click [e]
                       (google-sign-in))}]
        [ui/raised-button 
          {:label (localize-with-substitute :login (localize :email))
           :id "email-sign-in"
           :on-click (fn login-click [e]
                       (email-sign-in))}]]]))

(defn signin-modal []
  (let [sub-view (re-frame/subscribe [::subs/sub-view])]
    [ui/dialog
     {:title (localize :welcome-coffeepot)
      :open (= @sub-view :sign-in)
      :modal false
      :onRequestClose #(re-frame/dispatch [::events/sub-view ""])}
     [sign-in-selection]]))