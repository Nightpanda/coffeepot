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
                      :email "sähköposti"
                      :signup "Rekisteröidy"
                      :login "Kirjaudu sisään {1}:lla"
                      :logout "Kirjaudu ulos"
                      :no-brews "Olisiko aika keittää kahvit?"
                      :link-auth-username "Yhdistämme käyttämäsi tunnistautumismenetelmän käyttäjänimeesi"
                      :no-username-found "Käyttäjänimi on vapaana"
                      :save-username "Tallenna käyttäjänimi"
                      :username-warning "Käyttäjänimi on varatattu!"
                      :signup-with "Rekisteröidy {1}:lla"
                      :authenticated "Olet nyt tunnistautunut, voit jatkaa."
                      :add-description "Haluaisitko lisätä lyhyen kuvauksen itsestäsi?"
                      :save-description "Tallenna kuvaus"
                      :maybe-later "Teen tämän myöhemmin"
                      :authenticate "Tunnistaudu"
                      :register-username "Rekisteröi käyttäjänimesi"
                      :back "Takaisin"
                      :cancel "Peruuta"
                      :next "Seuraava"
                      :hello "Tervehdys {1}"
                      :welcome-coffeepot "Tervetuloa käyttämään Coffeepottia"
                      :add-experience "Lisää uusi kahvikirjaus"
                      :add "Lisää"
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
                      :email "email"
                      :signup "Sign up"
                      :login "Login with {1}"
                      :logout "Logout"
                      :no-brews "Is it time to brew a cup coffee?"
                      :link-auth-username "We will link your used authentication-method with your username"
                      :no-username-found "Username is available"
                      :save-username "Save username"
                      :username-warning "Warning! That username is taken"
                      :signup-with "Signup with {1}"
                      :authenticated "You are now authenticated, please continue."
                      :add-description "Would you like to write a short description?"
                      :save-description "Save description"
                      :maybe-later "I'll do it later."
                      :authenticate "Authenticate"
                      :register-username "Register username"
                      :back "Back"
                      :cancel "Cancel"
                      :next "Next"
                      :hello "Hello {1}"
                      :welcome-coffeepot "Welcome to Coffeepot."
                      :add-experience "Add a new coffee experience"
                      :add "Add"
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
