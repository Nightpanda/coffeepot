(ns coffeepot.views
  (:require [re-frame.core :as re-frame]
            [re-com.core :as re-com]
            [coffeepot.subs :as subs]
            ))

 (defn init []
 (js/firebase.initializeApp
   #js {:apiKey "AIzaSyABjP8bTTvEfy1n_pgR8oiqFBCn6hr4CP8"
        :authDomain "coffeepot-d13ea.firebaseapp.com"
        :databaseURL "https://coffeepot-d13ea.firebaseio.com"
        :storageBucket "coffeepot-d13ea.appspot.com"}))

(defn register [email password]
  (.. js/firebase auth (createUserWithEmailAndPassword email password)
      (catch (fn [e] (.log js/console "Register Error:" e)))))

(defn login-button []
  [re-com/button
   :label "nakki"
   ;:on-click (register "nakki" "mies")
   ])

(defn title []
  (let [name (re-frame/subscribe [::subs/name])]
    [re-com/title
     :label (str "Hello from " @name)
     :level :level1]))


(defn main-panel []
  [re-com/v-box
   :height "100%"
   :children [[title]
              [login-button]]])

;(init)
