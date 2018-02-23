(ns coffeepot.components.styles)

(def colors
  {:headerfooter-bg-color "#ffffff"
   :header-title-color "#d50038"
   :header-border-color "#747474"
   :title-color "#f9f9f9"
   :text-color "#1d1b1b"
   :text-color-alt "#f9f9f9"})

(def styles
  {:header-section {:align-items "center"}
   :header-item {:padding "5px 15px"
                 :font-size "12px"}
   :header-brand-item {:font-size "20px"
                       :line-height "0"
                       :color (:text-color colors)}
   :header {:background (:headerfooter-bg-color colors)
            :height "6em"
            :justify-content "space-between"
            :padding "1em 1em 1em 8em"
            :color (:header-title-color colors)
            :border-bottom (str "1px solid " (:header-border-color colors))}
   :title {:color (:text-color colors)}
   :title-box {:margin "1em 0em 1em 2em"
               :color (:title-color colors)
               :text-shadow (str "2px 2px " (:text-color colors))}
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
