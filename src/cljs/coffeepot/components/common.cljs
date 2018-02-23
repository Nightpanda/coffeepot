(ns coffeepot.components.common
  (:require [re-com.core :as re-com]
            [coffeepot.components.styles :as styles]))

(defn button [label on-click]
  [re-com/button
   :style {}
   :label label
   :on-click on-click])

(defn header-section [& elements]
  [re-com/h-box
   :style (:header-section styles/styles)
   :children (for [element elements]
               ^{:key element} element)])

(defn header-item [item]
  [re-com/box
   :style (:header-item styles/styles)
   :child item])

(defn header-brand-item [item]
  [re-com/box
   :style (:header-brand-item styles/styles)
   :child item])

(defn header [& sections]
  [re-com/h-box
   :style (:header styles/styles)
   :children (for [section sections]
               ^{:key section} section)])

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
