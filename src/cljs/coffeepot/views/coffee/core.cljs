(ns coffeepot.views.coffee.core
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [reagent.core :as r]
            [reagent.ratom :refer [atom]]
            [cljsjs.material-ui]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
            [cljs-time.core :refer [date-time]]
            [coffeepot.components.common :as c]
            [coffeepot.components.navheader :as nav]
            [coffeepot.events :as events]
            [coffeepot.subs :as subs]
            [coffeepot.views.signup.core :as signup]
            [coffeepot.firebase :as firebase]
            [coffeepot.localization :refer [localize localize-with-substitute]]
            [coffeepot.components.styles :as styles]))

(defn add-experience-dialog [dialog-open?]
  (let [experience-image "https://i.amz.mshcdn.com/tYrwgm0OdgYjCkcouBHOjm0pPhM=/950x534/filters:quality(90)/https%3A%2F%2Fblueprint-api-production.s3.amazonaws.com%2Fuploads%2Fcard%2Fimage%2F800629%2Fc4e52c80-d925-453b-905e-a0a37370b702.jpeg"
        experience (atom {:date ""
                          :description ""
                          :roastery ""
                          :rating 0})]
    (fn [dialog-open?]
      [ui/dialog {:full-screen ""
                  :Fullscreen ""
                  :fullScreen ""
                  :open @dialog-open?
                  :content-style {:height "100%"
                                  :width "100%"
                                  :max-width nil
                                  :max-height nil}
                  :Transition-component (goog.object/get js/MaterialUI "Slide")
                  ;;:Transition-component (r/reactify-component (fn [props] [(goog.object/get js/MaterialUI "Slide") (merge {:direction "up"} props)]))
                  :Transition-props {:direction "up"}
                  :on-close (fn []
                              (reset! dialog-open? false))}
       [ui/card
        (if (some? experience-image)
          [ui/card-media {:overlay (r/as-element [ui/card-title {:title (localize :add-experience)
                                                                 :titleStyle {:fontSize "2em"}}])}
           [:img {:src experience-image :height "350" :max-width "100%"}]]
          [ui/card-title {:title (localize :add-experience)
                          :titleStyle {:fontSize "2em"}}])
        [ui/card-text
         [ui/date-picker {:id "experience-date-picker"
                          :hintText "Päivämäärä"
                          :formatDate #(debug "paivamaara on" % "ja type on" (type %))}]]
        [ui/card-text {:style {:margin-top "-3em"}}
         [ui/text-field {:id "experience-description-field"
                         :full-width true
                         :floatingLabelText "Kuvaus"
                         :value (:description experience)}]]
        [ui/card-text {:style {:margin-top "-3em"}}
         [ui/text-field {:id "experience-roastery-field"
                         :full-width true
                         :floatingLabelText "Paahtimo"
                         :value (:roastery experience)}]]
        [ui/card-text {:style {:margin-top "-3em"}}
         [ui/text-field {:id "experience-rating-field"
                         :floatingLabelText "Arvio"}]]
        [ui/card-actions {:style {:text-align "right"}}
         [ui/raised-button
          {:primary true
           :on-click (fn [] (reset! dialog-open? false))}
          (localize :cancel)]
         [ui/raised-button
          {:primary true
           :on-click (fn [] (debug "Hibbel, adding"))}
          (localize :add)]]]])))

(defn app-main []
  (let [username (re-frame/subscribe [::subs/username])
        description (re-frame/subscribe [::subs/user-description])
        dialog-open? (atom false)]
    (fn []
      (cond
        (some? @username)
        [:div
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
                                           :on-click (fn logout-click [e]
                                                       (firebase/logout-auth))}]]
                     [c/app-content
                      [signup/user-description-modal]
                      [c/no-brews]
                      [re-com/v-box
                       :align :center
                       :children [[c/floating-add-button (fn []
                                                           (reset! dialog-open? true))]]]]]]

         [add-experience-dialog dialog-open?]]
        :else (re-frame/dispatch [::events/set-page :front])))))
