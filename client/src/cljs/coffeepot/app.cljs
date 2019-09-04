(ns coffeepot.app
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.History)
  (:require [secretary.core :as secretary]
            [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [reagent.core :as r]
            [goog.events :as goevents]
            [goog.history.EventType :as EventType]
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
            [coffeepot.theme.theme :as theme]))

(defn hook-browser-navigation! []
  (doto (History.)
    (goevents/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))))
    (.setEnabled true)))

(defn app-routes []
  (secretary/set-config! :prefix "#")

  (defroute "/" []
    (re-frame/dispatch [::events/set-page :front]))

  (defroute "/coffeepot" []
    (re-frame/dispatch [::events/set-page :coffeepot]))

  (hook-browser-navigation!))

(defmulti current-page #(let [page (re-frame/subscribe [::subs/active-page])]
                          (if (nil? @page) :default @page)))
(defmethod current-page :front []
  [front/front-page])
(defmethod current-page :coffeepot []
  [ui/mui-theme-provider {:mui-theme theme/coffeepot-theme} [coffee/app-main]])
(defmethod current-page :default []
  [front/front-page])

(defn app-root []
  (let [user-uid (re-frame/subscribe [::subs/user-uid])
        username (re-frame/subscribe [::subs/username])]
    (fn []
      [current-page])))
