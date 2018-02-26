(ns coffeepot.views.coffee.core
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [coffeepot.components.common :as c]
            [coffeepot.events :as events]
            [coffeepot.subs :as subs]))

(defn logout-auth []
  (println "logging out")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
      (.signOut (.auth @firebase-app))))

(defn app-main []
  (let [user (re-frame/subscribe [::subs/user])]
    (fn []
      [re-com/v-box
       :min-height "100vh"
       :children [[c/header
                   [c/header-section
                    [c/header-item
                     [c/header-brand-item
                      [:img {:src "images/weblogod.png" :height "80px" :width "80px"}]]]
                    [c/header-item
                     [c/header-brand-item
                      [c/title "Coffeepot"]]]]
                   [c/header-section
                    [c/header-item
                     [c/header-menu-item
                      "Kahvit"]]
                    [c/header-item
                     [c/header-menu-item
                      "Paahtimot"]]
                    [c/header-item
                     [c/header-menu-item
                      "Kahvilat"]]
                    [c/header-item
                     [c/header-menu-item
                      "Reseptit"]]]
                   [c/header-section
                    [c/header-item
                     [c/header-search-item
                      "Hae kahviasioita"
                      (fn []
                        ["Paulig" "Juhla Mokka" "Brazil" "Kulta Katriina"])]]]
                   [c/header-section
                    [c/header-item
                     [c/button "Logout" (fn login-click [e]
                                      (logout-auth))]]]]
                  [c/app-content
                   [:p (str "Hei käyttäjä " (:username @user))]]]])))
