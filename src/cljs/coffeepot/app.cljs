(ns coffeepot.app
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [mui-theme-provider]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [coffeepot.subs :as subs]
            [coffeepot.events :as events]
            [coffeepot.components.common :as c]
            [coffeepot.views.front.core :as front]
            [coffeepot.views.coffee.core :as coffee]
            [coffeepot.localization :refer [localize-with-substitute]]
            [coffeepot.theme.theme :as theme]))

(defn add-auth-listener []
  (println "Adding auth listener!")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        listener-alive? (re-frame/subscribe [::subs/auth-listener])]
    (if (and (not @listener-alive?) (some? @firebase-app))
      (do
        (re-frame/dispatch [::events/change-auth-listener-status true])
        (.onAuthStateChanged
         (.auth @firebase-app)
         (fn auth-state-changed [user-obj]
           (if (nil? user-obj)
             (re-frame/dispatch [::events/user-logged-in false])
             (re-frame/dispatch [::events/user-logged-in true])))
         (fn auth-error [error]
           (js/console.log error))))
      (println "Firebase app not present or listener already active!"))))

(defn main-panel []
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        listener-alive? (re-frame/subscribe [::subs/auth-listener])
        name (re-frame/subscribe [::subs/name])
        user (re-frame/subscribe [::subs/user])]
    (fn []
      (if (false? @listener-alive?)
        (add-auth-listener))
      (if (some? @firebase-app)
        (if (:logged-in @user)
          [ui/mui-theme-provider {:mui-theme theme/coffeepot-theme} [coffee/app-main]]
          [ui/mui-theme-provider {:mui-theme theme/coffeepot-theme} [front/login-page]])))))
