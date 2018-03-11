(ns coffeepot.views.front.core
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [coffeepot.components.common :as c]
            [coffeepot.components.navheader :as nav]
            [coffeepot.events :as events]
            [coffeepot.subs :as subs]
            [coffeepot.localization :refer [localize localize-with-substitute]]
            [coffeepot.components.styles :as styles]))

(defn provider []
  (new js/firebase.auth.GoogleAuthProvider))

(defn google-sign-in []
  (println "loggin in")
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
                                      :on-click (fn login-click [e]
                                                  (println "Sign up"))}
                                     {:label (localize-with-substitute :login (localize :google))
                                      :on-click (fn login-click [e]
                                                  (google-sign-in))}]]
                [c/content
                 [c/phone-preview-box
                  "1" "images/coffeepotphone.png"]
                 [c/title-box
                  "2" (localize :app-title)]]
                [ui/bottom-navigation {:style {:height "3em"
                                               :padding "1em"}}
                 [:p (localize :footer-text)]]]]))
