(ns coffeepot.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [coffeepot.app :as app]
            [coffeepot.config :as config]
            [coffeepot.events :as events]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [app/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (enable-console-print!)
  (println "Attemptin to initialize")
  (js/console.log "Let's get things started.")
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch [::events/initialize-firebase])
  (dev-setup)
  (mount-root))
