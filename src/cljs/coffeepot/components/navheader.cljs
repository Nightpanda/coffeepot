(ns coffeepot.components.navheader
  (:require [re-com.core :as re-com]
            [re-com.validate :refer [vector-of-maps?] :refer-macros [validate-args-macro]]
            [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
            [clojure.string :as str]
            [clojure.set :as set]
            [coffeepot.components.styles :as styles]))

(def navigation-menu-args
  [{:name :navigation-tabs
    :required false
    :type "vector of nav tabs"
    :validate-fn vector-of-maps?
    :description "Vector of navigation objects with text and alternative icon."}
   {:name :rightmost-buttons
    :required false
    :type "Vector of main action buttons"
    :validate-fn vector-of-maps?
    :description "Vector of maps for button labels and on-click functions"}])

(def navigation-header-args
  [{:name :site-logo
    :required false
    :type "string"
    :validate-fn string?
    :description "Site logo should be a string of the logo path."}
   {:name :site-title
    :required true
    :type "string"
    :validate-fn string?
    :description "Site title to be displayed."}
   {:name :navigation-tabs
    :required false
    :type "vector of nav tabs"
    :validate-fn vector-of-maps?
    :description "Vector of navigation objects with text and alternative icon."}
   {:name :navigation-right
    :required false
    :type "vector of navigation items for right"
    :validate-fn vector-of-maps?
    :description "Vector of navigation objects with text and on-click functions"}
   {:name :search-field
    :required false
    :type "map for search field"
    :validate-fn map?
    :description "Map of search field details"}
   {:name :rightmost-buttons
    :required false
    :type "Vector of main action buttons"
    :validate-fn vector-of-maps?
    :description "Vector of maps for button labels and on-click functions"}])

(defn nav-item [label icon]
  (let [on-click (fn []
                   (debug label))]
    [re-com/h-box
     :class "nav-item"
     :children [[re-com/box
                 :class "nav-text"
                 :child [ui/flat-button {:label label
                                         :on-click on-click}]]
                [re-com/box
                 :class "nav-icon"
                 :child [ui/icon-button {:on-click on-click}
                         [:img {:src icon :height "20px" :width "20px"}]]]]]))

(defn nav-search [placeholder data-source]
  [re-com/typeahead
   :style (:header-search-item styles/styles)
   :placeholder placeholder
   :data-source data-source])

(defn navigation-right-item [item]
  (fn []
    [re-com/box
     :child [ui/flat-button
             {:icon (r/as-element [:img {:src (:icon item)
                                         :width "20px"
                                         :height "20px"}])
              :on-click (:on-click item)}]]))

(defn nav-menu [& {:keys [navigation-tabs
                          rightmost-buttons] :as args}]
  {:pre [(validate-args-macro navigation-menu-args args "nav-menu")]}
  (let [navigation-tabs navigation-tabs
        rightmost-buttons rightmost-buttons
        menu-items (set/union navigation-tabs rightmost-buttons)]
    [re-com/h-box
     :style {:align-items "center"}
     :children [[:div.nav-menu
                 [ui/icon-menu {:icon-button-element (r/as-element
                                                      [ui/icon-button (r/as-element [ic/navigation-menu])])
                                :on-change (fn [event value]
                                             (debug value))}
                  (for [item menu-items]
                    [ui/menu-item {:key (:label item) :value (:label item) :primaryText (:label item)}])]]
                (for [nav-tab navigation-tabs]
                  ^{:key nav-tab} [nav-item (:label nav-tab) (:icon nav-tab)])]]))

(defn navigation-header [& {:keys [site-logo
                                   site-title
                                   navigation-tabs
                                   navigation-right
                                   search-field
                                   rightmost-buttons]
                            :as args}]
  {:pre [(validate-args-macro navigation-header-args args "navigation-header")]}
  (let [site-logo site-logo
        site-title site-title
        navigation-tabs navigation-tabs
        navigation-right navigation-right
        search-field search-field
        rightmost-buttons rightmost-buttons]
    [re-com/h-box
     :padding "0em 0em 0em 0em"
     :style {:flex-flow "row wrap"}
     :children [[re-com/h-box
                 :class "topbar-left"
                 :children [[re-com/box
                             :child [:img {:src (str site-logo) :height "80px" :width "80px"}]]
                            [re-com/box
                             :style {:color (:header-title-color styles/colors)
                                     :padding "0em 0em 0em 1em"}
                             :child [:h1 site-title]]]]
                [re-com/h-box
                 :class "topbar-right"
                 :size "auto"
                 :children [[nav-menu
                             :navigation-tabs navigation-tabs
                             :rightmost-buttons rightmost-buttons]
                            [re-com/box
                             :size "auto"
                             :child [:div]]
                            [re-com/h-box
                             :style {:align-items "center"}
                             :gap "2em"
                             :children [
                                        (if (some? navigation-right)
                                          (for [item navigation-right]
                                            ^{:key item} [navigation-right-item item]))
                                        (if (some? search-field)
                                          [nav-search
                                           (:placeholder search-field)
                                           (:search search-field)])
                                        (if (some? rightmost-buttons)
                                          (for [button rightmost-buttons]
                                            ^{:key button} [re-com/box
                                                            :class "nav-item"
                                                            :style {:padding "0em 0em 0em 1em"}
                                                            :child [ui/raised-button {:label (:label button)
                                                                                      :primary true
                                                                                      :on-click (:on-click button)}]]))]]]]]]))
