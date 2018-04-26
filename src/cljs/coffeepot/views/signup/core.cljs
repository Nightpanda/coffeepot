(ns coffeepot.views.signup.core
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
              [coffeepot.components.styles :as styles]))

(defn logout-auth []
    (debug "logging out")
    (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
        (.signOut (.auth @firebase-app))))

(defn provider []
  (new js/firebase.auth.GoogleAuthProvider))

(def register-form
    (fn []
    (let [user-uid (re-frame/subscribe [::subs/user-uid])
          new-username (r/atom "")]
        [ui/card
            [ui/card-text
              [:input.form-control
                {:on-change #(reset! new-username (-> % .-target .-value))}]]
            [ui/card-text "Live checking for the username will be given!"]
            [ui/card-text "We will link your google-authentication with this username"]
            [ui/card-actions
              [ui/flat-button {:on-click (fn []
                (events/save-username-uid @user-uid @new-username)
                #((re-frame/dispatch [::events/sub-view ""]))) :label "Save username"}]
              [ui/flat-button {:on-click (fn []
                                            #((re-frame/dispatch [::events/sub-view ""]))
                                            (logout-auth)) :label "Logout"}]]])))

(def sign-up-form
    (fn []
    (let [user-uid (re-frame/subscribe [::subs/user-uid])
          new-username (r/atom "")]
        [ui/card
            [ui/card-header [ui/flat-button {:on-click (fn [] (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
    (.signInWithPopup (.auth @firebase-app) (provider)))) :label "Signup with Google"} ]]
            [ui/card-text "After authentication, you can choose a username"]

            [ui/card-actions

              [ui/flat-button {:on-click #((re-frame/dispatch [::events/sub-view ""])) :label "Cancel"}]]
   ])))

(defn signup-modal []
  (let [user (re-frame/subscribe [::subs/user])
        sub-view (re-frame/subscribe [::subs/sub-view])]
    [ui/dialog
     {:title "Welcome to Coffeepot. Please sign up using your google account."
      :open (= @sub-view :sign-up)
      :modal false
      :onRequestClose #((re-frame/dispatch [::events/sub-view ""]))}
     [sign-up-form]]))

(defn register-username []
  (let [sub-view (re-frame/subscribe [::subs/sub-view])]
    [ui/dialog
     {:title "Welcome to Coffeepot. Please register a username"
      :open (= @sub-view :register)
      :modal false
      :onRequestClose #((re-frame/dispatch [::events/sub-view ""]))}
     [register-form]]))
