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
    (re-frame/dispatch [::events/user-uid nil])
    (re-frame/dispatch [::events/username nil])
    (.signOut (.auth @firebase-app))))

(defn provider []
  (new js/firebase.auth.GoogleAuthProvider))

(defn abort-sign-up! []
  (re-frame/dispatch [::events/sub-view ""])
  (logout-auth))

(def register-form
  (fn []
    (let [user-uid (re-frame/subscribe [::subs/user-uid])
          new-username (r/atom "")]
      [ui/card
       [ui/card-text
        [:input.form-control
         {:on-change #(reset! new-username (-> % .-target .-value))}]]
       [ui/card-text "We will link your google-authentication with this username"]
       [ui/card-actions
        [events/listen-firebase-db (str "usernames/")
         (fn [usernames]
           (if (some? @usernames)
             (if (empty? (filter #(= % @new-username) (js/Object.keys @usernames)))
               (do
                 [ui/card-text "No username found, you all good."]
                 [ui/flat-button {:on-click (fn []
                                              (events/save-username-uid @user-uid @new-username)
                                              (re-frame/dispatch [::events/sub-view ""])) :label "Save username"}])
               [ui/card-text "Warning! That username is taken"])))]]])))

(def authenticate-step
  (fn []
    (let [user-uid (re-frame/subscribe [::subs/user-uid])]
      [ui/card
       (if (nil? @user-uid)
         [ui/card-header [ui/flat-button {:on-click (fn [] (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
                                                             (.signInWithPopup (.auth @firebase-app) (provider)))) :label "Signup with Google"} ]]
         [ui/card-header "You are now authenticated."])])))

(def register-username-step
  (fn []
    [register-form]))

(defn step-> [step]
  (let [inc-step (inc step)]
    (if (> inc-step 1)
      1
      inc-step)))

(defn step<- [step]
  (let [dec-step (dec step)]
    (if (< dec-step 0)
      0
      dec-step)))

(def user-description-step
  (fn []
    (let [user-uid (re-frame/subscribe [::subs/user-uid])
          username (re-frame/subscribe [::subs/username])
          user-description (re-frame/subscribe [::subs/user-description])
          new-user-description (r/atom "")]
      [ui/card
       [ui/card-header "Would you like to write a short description?"]
       [ui/card-text
        [:input.form-control
         {:on-change #(reset! new-user-description (-> % .-target .-value))}]]
       [ui/flat-button {:on-click (fn []
                                    (events/save-user-description @user-uid @new-user-description)) :label "Save description"}]
       [ui/card-text "This won't be public until you make it so."]
       (if (nil? @user-description)
         [ui/flat-button {:on-click
                          (fn [] (re-frame/dispatch [::events/user-description ""])
                            (events/save-user-description @user-uid "")) :label "I'll do it later."}])])))

(def sign-up-form
  (let [user-uid (re-frame/subscribe [::subs/user-uid])
        username (re-frame/subscribe [::subs/username])
        initial-step (cond
                       (some? @user-uid) 1
                       :else 0)
        step (r/atom initial-step)]
    (fn []
      [ui/card
       [ui/card-text
        (cond
          (= @step 0) [authenticate-step]
          (= @step 1) [register-username-step])]
       [ui/stepper {:activeStep @step}
        [ui/step
         [ui/step-label "Authenticate"]]
        [ui/step
         [ui/step-label "Register username"]]]
       [ui/card-actions
        (if (> @step 0)
          [ui/flat-button {:on-click #(swap! step step<-)} "Back"]
          [ui/flat-button {:on-click #(abort-sign-up!) :label "Cancel"}])
        (cond
          (= @step 0) (if (some? @user-uid)
                        [ui/raised-button {:on-click #(swap! step step->)} "Next"]))]])))

(defn signup-modal []
  (let [sub-view (re-frame/subscribe [::subs/sub-view])]
    [ui/dialog
     {:title "Welcome to Coffeepot."
      :open (= @sub-view :sign-up)
      :modal false
      :onRequestClose #(re-frame/dispatch [::events/sub-view ""])}
     [sign-up-form]]))

(defn user-description-modal []
  (let [user-uid (re-frame/subscribe [::subs/user-uid])
        username (re-frame/subscribe [::subs/username])
        description (re-frame/subscribe [::subs/user-description])]
    [ui/dialog
     {:title (str "Hello " @username)
      :open (and (some? @user-uid) (some? @username) (nil? @description))
      :modal false
      :onRequestClose (fn [] (re-frame/dispatch [::events/description ""]))}
     [user-description-step]]))
