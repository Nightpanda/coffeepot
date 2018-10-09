(ns coffeepot.views.profile.core
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
  ;;          [reagent.core :as r]
    ;;        [cljsjs.material-ui]
     ;;       [cljs-react-material-ui.reagent :as ui]
      ;;      [cljs-react-material-ui.icons :as ic]

           [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
      ;;      [coffeepot.components.common :as c]
         ;;   [coffeepot.components.navheader :as nav]
        ;;    [coffeepot.events :as events]
       ;;     [coffeepot.subs :as subs]
       ;;     [coffeepot.views.signup.core :as signup]
       ;;     [coffeepot.firebase :as firebase]
            [coffeepot.localization :refer [localize localize-with-substitute]]
       ;;     [coffeepot.components.styles :as styles]
            ))

(defn profile-modal [show?]
  (fn [show?]
    [re-com/v-box
     :min-height "100vh"
     :children [(when @show?
                  [re-com/modal-panel
                   :backdrop-on-click #(reset! show? false)
                   :child [:h1 {:id "profile-page-header"} (localize :profile-page-header)]])]]))
