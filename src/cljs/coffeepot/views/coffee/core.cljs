(ns coffeepot.views.coffee.core
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
            [coffeepot.views.signup.core :as signup]
            [coffeepot.localization :refer [localize localize-with-substitute]]
            [coffeepot.components.styles :as styles]))

(defn logout-auth []
  (debug "logging out")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
      (.signOut (.auth @firebase-app))
      (re-frame/dispatch [::events/username nil])))

(defn app-main []
  (let [username (re-frame/subscribe [::subs/username])]
    (fn []
      [re-com/v-box
       :min-height "100vh"
       :children [[nav/navigation-header
                   :site-logo "images/brandlogo_s_web.png"
                   :site-title (localize :app-title)
                   :navigation-tabs [{:label (localize :coffees) :icon "images/weblogod.png"}
                                     {:label (localize :roasters) :icon "images/weblogod.png"}
                                     {:label (localize :cafes) :icon "images/weblogod.png"}
                                     {:label (localize :recipes) :icon "images/weblogod.png"}]
                   :search-field {:placeholder (localize :search)
                                  :search (fn []
                                            ["Paulig" "Juhla Mokka" "Brazil" "Kulta Katriina"])}
                   :rightmost-buttons [{:label (localize :logout)
                                        :on-click (fn login-click [e]
                                                    (logout-auth))}]]
                  [c/app-content
                   [signup/register-username]
                   [c/no-brews]
                   [re-com/box
                    :class "just-a-test"
                    :child [ui/card-text (str "Hei käyttäjä " @username)]]]]])))
