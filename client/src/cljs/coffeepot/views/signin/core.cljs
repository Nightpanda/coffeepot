(ns coffeepot.views.signin.core
    (:require [re-frame.core :as re-frame]
              [reagent.core :as r]
              [re-com.core :as re-com]
              [clojure.string :as str]
              [cljsjs.material-ui]
              [cljs-react-material-ui.reagent :as ui]
              [cljs-react-material-ui.icons :as ic]
              [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
              [coffeepot.api :as api]
              [coffeepot.events :as events]
              [coffeepot.subs :as subs]
              [coffeepot.localization :refer [localize localize-with-substitute]]
              [coffeepot.components.styles :as styles]))

(defn login-with-options []
  [re-com/h-box
   :justify :center
   :children [[re-com/v-box
               :gap "1em"
               :align :center
               :children [
                          [re-com/button
                           :attr {:id "google-sign-in"}
                           :on-click #(println "hippeltron")
                           :label [re-com/h-box
                                   :width "200px"
                                   :children [[:img {:src "images/g-logo.png"
                                                     :width "24px"
                                                     :height "24px"}]
                                              [re-com/box
                                               :align :center
                                               :justify :center
                                               :size "auto"
                                               :child (localize-with-substitute :login-with (localize :google))]]]]]]]])

(defn login-with-email []
  (let [email-address (r/atom "")
        password (r/atom "")]
    (fn []
      [re-com/h-box
       :justify :center
       :padding "2em 2em 2em 2em"
       :style {:border "1px solid lightgrey"}
       :children [[re-com/v-box
                   :gap "1em"
                   :align :stretch
                   :children [[re-com/v-box
                               :gap "1em"
                               :size "0 0 auto"
                               :children [[re-com/h-box
                                           :align :center
                                           :gap "0.2em"
                                           :children [[re-com/input-text
                                                       :attr {:id "email-address-input"}
                                                       :model ""
                                                       :change-on-blur? false
                                                       :on-change (fn [new-value]
                                                                    (reset! email-address new-value))
                                                       :placeholder (localize :email-address)]]]
                                          [re-com/h-box
                                           :align :center
                                           :gap "0.2em"
                                           :children [[re-com/input-text
                                                       :attr {:id "email-password-input"}
                                                       :model ""
                                                       :input-type :password
                                                       :change-on-blur? false
                                                       :on-change (fn [new-value]
                                                                    (reset! password new-value))
                                                       :placeholder (localize :password)]]]]]
                              [re-com/box
                               :align :center
                               :child [re-com/button
                                       :attr {:id "email-sign-in"}
                                       :on-click (fn []
                                                   (re-frame/dispatch [::events/sub-view nil])
                                                   (api/authenticate-user @email-address @password))
                                       :style {:color "white"
                                               :background-color "green"}
                                       :label [re-com/h-box
                                               :width "200px"
                                               :children [[re-com/box
                                                           :align :center
                                                           :justify :center
                                                           :size "auto"
                                                           :child (localize :login)]]]]]]]]])))

(defn sign-in-selection []
  [ui/card
   [re-com/v-box
    :align :center
    :gap "1em"
    :padding "0em 0em 2em 0em"
    :children [[ui/card-text (localize :select-sign-in-method)]
               [login-with-options]
               [login-with-email]]]])

(defn signin-modal []
  (let [sub-view (re-frame/subscribe [::subs/sub-view])]
    [ui/dialog
     {:title (localize :sign-in-to-coffeepot)
      :open (= @sub-view :sign-in)
      :modal false
      :onRequestClose #(re-frame/dispatch [::events/sub-view ""])}
     [sign-in-selection]]))
