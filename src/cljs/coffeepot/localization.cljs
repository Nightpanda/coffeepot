(ns coffeepot.localization
    (:require [tongue.core :as tongue]
              [re-frame.core :refer [subscribe]]
              [coffeepot.subs :as subs]))

(def dictionary {
    :fi {
        :login "Kirjaudu sisään {1}:lla"
        :logout "Kirjaudu ulos"
    }
    :en 
    {
        :login "Login with {1}"
        :logout "Logout"
    }
    :tongue/fallback :fi
})

(def translate
  (tongue/build-translate dictionary))

(defn localize [text-key]
    (let [locale (subscribe [::subs/locale])]
        (translate @locale text-key))
)

(defn localize-with-substitute [text-key substitution]
    (let [locale (subscribe [::subs/locale])]
        (translate @locale text-key substitution))
)