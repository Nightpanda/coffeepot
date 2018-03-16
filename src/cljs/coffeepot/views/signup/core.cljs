(ns coffeepot.views.signup.core
    (:require [re-frame.core :as re-frame]
              [re-com.core :as re-com]
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
            [re-com/p "Welcome to Coffepot. Please sign up using your google account."]
            [:button "Signup with Google"]
            [re-com/p "After authentication, you can choose a username"]
            [:input {:type "text"}]
            [re-com/p "Live checking for the username will be given!"]
            [re-com/p "We will link your google-authentication with this username"]]))
  
(defn signup-modal []
    (let [user (re-frame/subscribe [::subs/user])]
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
                                    :on-click (fn login-click [e]
                                                (re-frame/dispatch [::events/current-view :sign-up])
                                                (debug "Sign up"))}
                                    {:label (localize-with-substitute :login (localize :google))
                                    :on-click (fn login-click [e]
                                                )}]]
                        [c/app-content
                        [re-com/modal-panel
                        :backdrop-color   "grey"
                        :backdrop-opacity 0.4
                        :style            {:font-family "Consolas"}
                        :child            [sign-up-form]]]]])))
