(ns coffeepot.localization
    (:require [tongue.core :as tongue]
              [re-frame.core :refer [subscribe]]
              [coffeepot.subs :as subs]))

(def dictionary {
                 :fi {
                      :app-title "Coffeepot"
                      :footer-text "© 2018 - Rakkaudesta kahviin. Coffeepot Inc."
                      :coffees "Kahvit"
                      :roasters "Paahtimot"
                      :cafes "Kahvilat"
                      :recipes "Reseptit"
                      :search "Hae"
                      :google "Google"
                      :signup "Rekisteröidy"
                      :login "Kirjaudu sisään {1}:lla"
                      :logout "Kirjaudu ulos"
                      :no-brews "Olisiko aika keittää kahvit?"
                      }
                 :en {
                      :app-title "Coffeepot"
                      :footer-text "© 2018 - Brewed with love by Coffeepot Inc."
                      :coffees "Coffees"
                      :roasters "Roasters"
                      :cafes "Cafes"
                      :recipes "Recipes"
                      :search "Search"
                      :google "Google"
                      :signup "Sign up"
                      :login "Login with {1}"
                      :logout "Logout"
                      :no-brews "Is it time to brew a cup coffee?"
                      }
                 :tongue/fallback :fi
                 })

(def translate
  (tongue/build-translate dictionary))

(defn localize [text-key]
  (let [locale (subscribe [::subs/locale])]
    (translate @locale text-key)))

(defn localize-with-substitute [text-key substitution]
  (let [locale (subscribe [::subs/locale])]
    (translate @locale text-key substitution)))
