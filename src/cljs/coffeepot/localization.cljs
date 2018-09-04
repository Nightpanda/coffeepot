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
                      :email-address "Sähköpostiosoite"
                      :email-invalid "Virheellinen sähköpostiosoite"
                      :password "Salasana"
                      :password-again "Salasana (uudestaan)"
                      :continue "Jatka"
                      :signup "Rekisteröidy"
                      :create-account "Luo Coffeepot-tili / Rekisteröidy"
                      :create-account-button "Luo tili"
                      :terms-read-and-accepted "Olen lukenut ja hyväksyn käyttöehdot"
                      :username "Käyttäjätunnus"
                      :username-min "Käyttäjätunnus (min. 3 merkkiä)"
                      :login "Kirjaudu"
                      :login-with "Kirjaudu sisään {1}:lla"
                      :logout "Kirjaudu ulos"
                      :no-brews "Olisiko aika keittää kahvit?"
                      :link-auth-username "Yhdistämme käyttämäsi tunnistautumismenetelmän käyttäjänimeesi"
                      :username-available "Käyttäjänimi on vapaana"
                      :save-username "Tallenna käyttäjänimi"
                      :username-taken "Käyttäjänimi käytössä!"
                      :signup-with "Rekisteröidy {1}:lla"
                      :authenticated "Olet nyt tunnistautunut, voit jatkaa."
                      :sign-in-to-coffeepot "Kirjaudu Coffeepotiin"
                      :select-sign-in-method "Valitse kirjautumistapa"
                      :add-description "Haluaisitko lisätä lyhyen kuvauksen itsestäsi?"
                      :save-description "Tallenna kuvaus"
                      :maybe-later "Teen tämän myöhemmin"
                      :authenticate "Tunnistaudu"
                      :select-sign-up-method "Valitse rekisteröitymistapa"
                      :fill-account-details "Täytä käyttäjätiedot"
                      :register-username "Rekisteröi käyttäjänimesi"
                      :register-with-email "Rekisteröidy sähköpostilla"
                      :or "tai"
                      :back "Takaisin"
                      :cancel "Peruuta"
                      :next "Seuraava"
                      :hello "Tervehdys {1}"
                      :welcome-coffeepot "Tervetuloa käyttämään Coffeepottia"
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
                      :email "Email"
                      :email-address "Email address"
                      :email-invalid "Invalid email address"
                      :password "Password"
                      :password-again "Password again"
                      :continue "Continue"
                      :signup "Sign up"
                      :create-account "Create Coffeepot account / Register"
                      :create-account-button "Create account"
                      :terms-read-and-accepted "I have read and agree to terms"
                      :username "Username"
                      :username-min "Username (min. 3 characters)"
                      :login "Login"
                      :login-with "Login with {1}"
                      :logout "Logout"
                      :no-brews "Is it time to brew a cup coffee?"
                      :link-auth-username "We will link your used authentication-method with your username"
                      :username-available "Username is available"
                      :save-username "Save username"
                      :username-taken "Username taken"
                      :signup-with "Signup with {1}"
                      :authenticated "You are now authenticated, please continue."
                      :sign-in-to-coffeepot "Login to Coffeepot"
                      :select-sign-in-method "Select sign in method"
                      :add-description "Would you like to write a short description?"
                      :save-description "Save description"
                      :maybe-later "I'll do it later."
                      :authenticate "Authenticate"
                      :select-sign-up-method "Select sign-up method"
                      :fill-account-details "Fill account details"
                      :register-username "Register username"
                      :register-with-email "Register with email"
                      :or "or"
                      :back "Back"
                      :cancel "Cancel"
                      :next "Next"
                      :hello "Hello {1}"
                      :welcome-coffeepot "Welcome to Coffeepot."
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
