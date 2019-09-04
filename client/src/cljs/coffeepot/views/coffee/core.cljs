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
            [coffeepot.components.styles :as styles]
            [coffeepot.views.profile.core :as profile-page]))

(defn app-main []
  (let [username (re-frame/subscribe [::subs/username])
        description (re-frame/subscribe [::subs/user-description])
        show-profile? (r/atom false)]
    (fn []
      (cond
        (some? @username)
        [re-com/v-box
         :min-height "100vh"
         :children [
                    [nav/navigation-header
                     :site-logo "images/brandlogo_s_web.png"
                     :site-title (localize :app-title)
                     :navigation-tabs [{:label (localize :coffees) :icon "images/weblogod.png"}
                                       {:label (localize :roasters) :icon "images/weblogod.png"}
                                       {:label (localize :cafes) :icon "images/weblogod.png"}
                                       {:label (localize :recipes) :icon "images/weblogod.png"}]
                     :search-field {:placeholder (localize :search)
                                    :search (fn []
                                              ["Paulig" "Juhla Mokka" "Brazil" "Kulta Katriina"])}
                     :rightmost-buttons [{:id "profile-page-link"
                                          :label (localize :profile-page-link)
                                          :on-click #(reset! show-profile? true)}
                                         {:label (localize :logout)
                                          :on-click (fn logout-click [e]
                                                      (re-frame/dispatch [::events/username nil])
                                                      (re-frame/dispatch [::events/user-uid nil]))}]]
                    [c/app-content
                     [signup/user-description-modal]
                     [c/no-brews]
                     [profile-page/profile-modal show-profile?]
                     [re-com/box
                      :attr {:id "description-box"}
                      :class "just-a-test"
                      :child [ui/card-text (str "Hei käyttäjä " @username " Kuvauksesi oli " @description)]]]]]
        :else (re-frame/dispatch [::events/set-page :front])))))
