(ns coffeepot.views.signup.core
    (:require [re-frame.core :as re-frame]
              [reagent.core :as r]
              [cljsjs.material-ui]
              [cljs-react-material-ui.reagent :as ui]
              [cljs-react-material-ui.icons :as ic]
              [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
              [coffeepot.components.common :as c]
              [coffeepot.components.navheader :as nav]
              [coffeepot.events :as events]
              [coffeepot.subs :as subs]
              [coffeepot.localization :refer [localize localize-with-substitute]]
              [coffeepot.components.styles :as styles]))

(defn logout-auth []
    (debug "logging out")
    (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
        (.signOut (.auth @firebase-app))))

(def sign-up-form
    (fn []
        [:div
            [:button "Signup with Google"]
            [:p "After authentication, you can choose a username"]
            [:input {:type "text"}]
            [:p "Live checking for the username will be given!"]
            [:p "We will link your google-authentication with this username"]]))

(defn signup-modal []
  (let [user (re-frame/subscribe [::subs/user])
        sub-view (re-frame/subscribe [::subs/sub-view])]
    [ui/dialog
     {:title "Welcome to Coffepot. Please sign up using your google account."
      :open (= @sub-view :sign-up)
      :modal false
      :onRequestClose #((re-frame/dispatch [::events/sub-view ""]))}
     [sign-up-form]]))
