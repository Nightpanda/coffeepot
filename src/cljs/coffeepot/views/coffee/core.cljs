(ns coffeepot.views.coffee.core
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

(defn logout-auth []
  (println "logging out")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
      (.signOut (.auth @firebase-app))))

(defn app-bar []
  (fn []
    [ui/toolbar-group
     [ui/flat-button {:label (localize :coffees)}]
     [ui/flat-button {:label (localize :roasters)}]
     [ui/flat-button {:label (localize :cafes)}]
     [ui/flat-button {:label (localize :recipes)}]]))

(defn app-main []
  (let [user (re-frame/subscribe [::subs/user])]
    (fn []
      [re-com/v-box
       :min-height "100vh"
       :children [[ui/toolbar {:style (:header styles/styles)}
                   [ui/toolbar-group {:first-child true}
                    (r/as-element [:img {:src "images/brandlogo_s_web.png" :height "80px" :width "80px"}])
                    (r/as-element (localize :app-title))]
                   [app-bar]
                   [ui/toolbar-group
                    [ui/toolbar-separator]]
                   [ui/toolbar-group
                    [c/header-search-item
                     (localize :search)
                     (fn []
                       ["Paulig" "Juhla Mokka" "Brazil" "Kulta Katriina"])]
                    [ui/raised-button {:label (localize :logout)
                                       :primary true
                                       :on-click (fn login-click [e]
                                                   (logout-auth))}]]]
                  [c/app-content
                   [:p (str "Hei käyttäjä " (:username @user))]]]])))
