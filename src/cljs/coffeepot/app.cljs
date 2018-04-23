(ns coffeepot.app
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.core :refer [mui-theme-provider]]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
            [coffeepot.subs :as subs]
            [coffeepot.events :as events]
            [coffeepot.components.common :as c]
            [coffeepot.views.front.core :as front]
            [coffeepot.views.coffee.core :as coffee]
            [coffeepot.localization :refer [localize-with-substitute]]
            [coffeepot.theme.theme :as theme]))

(defn add-auth-listener []
  (debug "Adding auth listener!")
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        listener-alive? (re-frame/subscribe [::subs/auth-listener])]
    (if (and (not @listener-alive?) (some? @firebase-app))
      (do
        (re-frame/dispatch [::events/change-auth-listener-status true])
        (.onAuthStateChanged
         (.auth @firebase-app)
         (fn auth-state-changed [user-obj]
           (if (nil? user-obj)
            (do 
              (re-frame/dispatch [::events/set-user-uid nil])
              (re-frame/dispatch [::events/current-view :logged-out]))
            (do 
              (re-frame/dispatch [::events/set-user-uid (.-uid user-obj)])
              (re-frame/dispatch [::events/current-view :logged-in])
              (events/get-username! (.-uid user-obj)))))
         (fn auth-error [error]
           (debug error))))
      (debug "Firebase app not present or listener already active!"))))

(defn main-panel []
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        listener-alive? (re-frame/subscribe [::subs/auth-listener])
        name (re-frame/subscribe [::subs/name])
        user (re-frame/subscribe [::subs/user])
        current-view (re-frame/subscribe [::subs/current-view])]
    (fn []
      (if (false? @listener-alive?)
        (add-auth-listener))
        (debug @current-view)
      (if (some? @firebase-app)
        (case @current-view
          :logged-in [ui/mui-theme-provider {:mui-theme theme/coffeepot-theme} [coffee/app-main]]
          :logged-out [ui/mui-theme-provider {:mui-theme theme/coffeepot-theme} [front/login-page]])))))
