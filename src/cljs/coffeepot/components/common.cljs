(ns coffeepot.components.common
  (:require [re-com.core :as re-com]
            [re-com.validate :refer [vector-of-maps?] :refer-macros [validate-args-macro]]
            [reagent.core :as r]
            [cljsjs.material-ui]
            [cljs-react-material-ui.reagent :as ui]
            [cljs-react-material-ui.icons :as ic]
            [coffeepot.localization :refer [localize localize-with-substitute]]
            [coffeepot.components.styles :as styles]
            [clojure.string :as str]))

(defn no-brews [text]
  [re-com/box
   :align :center
   :child [re-com/title
           :margin-top "2em"
           :margin-bottom "2em"
           :style (:no-brews-greeting styles/styles)
           :label (localize :no-brews)
           :level :level1]])

(defn app-content [& elements]
  [re-com/v-box
   :style (:app-content styles/styles)
   :size "1"
   :children (for [element elements]
               ^{:key element} element)])

(defn content [& elements]
  [re-com/h-box
   :style (:content styles/styles)
   :size "1"
   :children (for [element elements]
               ^{:key element} element)])

(defn phone-preview-box [size img]
  [re-com/v-box
   :size size
   :align :stretch
   :align-self :center
   :children [[:img {:src img
                     :width "100%"}]]])

(defn title [title-text]
  [re-com/title
   :style (:title styles/styles)
   :label title-text
   :level :level1])

(defn title-box [size text]
  [re-com/v-box
   :style (:title-box styles/styles)
   :size size
   :align :center
   :align-self :center
   :children [[re-com/box
               :style {:font-size "8em"}
               :child text]]])

(defn footer [text]
  [re-com/box
   :style (:footer styles/styles)
   :child text])
