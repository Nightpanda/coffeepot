(ns coffeepot.theme.theme
  (:require [cljs-react-material-ui.core :refer [get-mui-theme color]]))

(def coffeepot-palette
  {:palette {
             :primary1Color "#ffffff"
             :textColor "#1d1b1b"
             :alternateTextColor "#d50038"
             :accent2Color "#000000"
             :accent1Color "#000000"
             :tableDetailsBackgroundColor (color "grey100")
             :header-border-color "#747474"
             :app-content-bg-color "#e6e4e5"}})

(def coffeepot-theme
  (get-mui-theme coffeepot-palette))
