(ns coffeepot.views.front.core
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [mui-theme-provider]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
            [coffeepot.components.common :as c]
            [coffeepot.components.navheader :as nav]
            [coffeepot.events :as events]
            [coffeepot.subs :as subs]
            [coffeepot.views.signup.core :as signup]
            [coffeepot.views.signin.core :as signin]
            [coffeepot.localization :refer [localize localize-with-substitute]]
            [coffeepot.components.styles :as styles]
            [coffeepot.views.coffee.core :as coffee]
            [coffeepot.theme.theme :as theme]
            [coffeepot.firebase :as firebase]))

(firebase/add-auth-listener)

(defn provider []
  (new js/firebase.auth.GoogleAuthProvider))

(defn google-sign-in []
  (debug "loggin in")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
    (.signInWithPopup (.auth @firebase-app) (provider))))

(defn login-page []
  (fn []
    [re-com/v-box
     :min-height "100vh"
     :children [[nav/navigation-header
                 :site-logo "images/brandlogo_s_web.png"
                 :site-title (localize :app-title)
                 :navigation-right [{:icon "https://lipis.github.io/flag-icon-css/flags/1x1/fi.svg"
                                     :on-click (fn [] (re-frame/dispatch [::events/change-locale :fi]))}
                                    {:icon "https://lipis.github.io/flag-icon-css/flags/1x1/gb.svg"
                                     :on-click (fn [] (re-frame/dispatch [::events/change-locale :en]))}]
                 :rightmost-buttons [{:label (localize :signup)
                                      :id "sign-up"
                                      :on-click (fn login-click [e]
                                                  (re-frame/dispatch [::events/sub-view :sign-up]))}
                                     {:label (localize-with-substitute :login (localize :google))
                                      :id "sign-in"
                                      :on-click (fn login-click [e]
                                                  (re-frame/dispatch [::events/sub-view :sign-in]))}]]
                [c/content
                 [signup/signup-modal]
                 [signin/signin-modal]
                 [c/phone-preview-box
                  "1" "images/coffeepotphone.png"]
                 [c/title-box
                  "2" (localize :app-title)]]
                [ui/bottom-navigation {:style {:height "3em"
                                               :padding "1em"}}
                 [:p (localize :footer-text)]]]]))

(defn front-page []
  (let [user-uid (re-frame/subscribe [::subs/user-uid])
        username (re-frame/subscribe [::subs/username])]
    (debug "useruid" @user-uid "username" @username)
    (fn []
      (cond
        (and (some? @user-uid) (some? @username)) (re-frame/dispatch [::events/set-page :coffeepot])
        :else [ui/mui-theme-provider {:mui-theme theme/coffeepot-theme} [login-page]]))))
