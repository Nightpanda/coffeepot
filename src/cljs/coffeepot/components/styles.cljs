(ns coffeepot.components.styles)

(def colors
  {:headerfooter-bg-color "#ffffff"
   :header-title-color "#d50038"
   :header-border-color "#747474"
   :title-color "#f9f9f9"
   :text-color "#1d1b1b"
   :text-color-alt "#f9f9f9"
   :app-content-bg-color "#e6e4e5"})

(def styles
  {:header-search-item {}
   :title {:color (:text-color colors)}
   :title-box {:margin "1em 0em 1em 2em"
               :color (:title-color colors)
               :text-shadow (str "2px 2px " (:text-color colors))}
   :no-brews-greeting {:color "#666"
                       :text-shadow "0px -1px 0px rgba(0,0,0,.5)"}
   :app-content {:padding "1em"
                 :background (:app-content-bg-color colors)}
   :content {:padding "1em"
             :background-size "cover"
             :background-position "center"
             :background-repeat "no-repeat"
             :background-image "url(images/main.jpg)"
             :opacity "0.9"}
   :footer {:background (:headerfooter-bg-color colors)
            :height "3em"
            :padding "1em"
            :color (:text-color colors)}})
