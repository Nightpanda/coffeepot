(ns coffeepot.views.front.core
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [coffeepot.components.common :as c]
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

(defn front-page-bar []
  (fn []
    [ui/toolbar-group
     [ui/flat-button {:icon
                        (r/as-element [:img {:src "https://lipis.github.io/flag-icon-css/flags/1x1/fi.svg"
                                             :height "20px"
                                             :width "30px"}])
                        :primary true
                        :on-click (fn [] (re-frame/dispatch [::events/change-locale :fi]))}]
     [ui/flat-button {:icon
                        (r/as-element [:img {:src "https://lipis.github.io/flag-icon-css/flags/1x1/gb.svg"
                                             :height "20px"
                                             :width "30px"}])
                        :primary true
                        :on-click (fn [] (re-frame/dispatch [::events/change-locale :en]))}]
     [ui/raised-button {:label (localize-with-substitute :login (localize :google))
                        :primary true
                        :on-click (fn login-click [e]
                                    (google-sign-in))}]]))

(defn login-page []
  (fn []
    [re-com/v-box
     :min-height "100vh"
     :children [[ui/app-bar {:title (localize :app-title)
                             :icon-style-left {:margin "0em"
                                               :padding "0em 2em 0em 8em"}
                             :title-style {:font-size "3em"}
                             :style {:align-items "center"}
                             :icon-element-left
                             ;;(r/as-element [:img {:src "images/weblogod.png" :height "80px" :width "80px"}])
                             (r/as-element [:img {:src "images/brandlogo_s_web.png" :height "80px"}])
                             :icon-element-right
                             (r/as-element [front-page-bar])}]
                [c/content
                 [c/phone-preview-box
                  "1" "images/coffeepotphone.png"]
                 [c/title-box
                  "2" (localize :app-title)]]
                [ui/bottom-navigation {:style {:height "3em"
                                               :padding "1em"}}
                 [:p (localize :footer-text)]]]]))
