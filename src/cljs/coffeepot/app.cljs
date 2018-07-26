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
            [coffeepot.theme.theme :as theme]
            [coffeepot.firebase :as firebase]))

(firebase/add-auth-listener)

(defn main-panel []
  (let [firebase-app (re-frame/subscribe [::subs/firebase-app])
        listener-alive? (re-frame/subscribe [::subs/auth-listener])
        user-uid (re-frame/subscribe [::subs/user-uid])
        username (re-frame/subscribe [::subs/username])]
    (fn []
      (if (false? @listener-alive?)
        (firebase/add-auth-listener))
      (if (some? @firebase-app)
        (cond
          (and (some? @user-uid) (some? @username)) [ui/mui-theme-provider {:mui-theme theme/coffeepot-theme} [coffee/app-main]]
          (and (some? @user-uid) (nil? @username)) (do (re-frame/dispatch [::events/sub-view :sign-up])
                                                     [ui/mui-theme-provider {:mui-theme theme/coffeepot-theme} [front/login-page]])
          :else [ui/mui-theme-provider {:mui-theme theme/coffeepot-theme} [front/login-page]])))))
