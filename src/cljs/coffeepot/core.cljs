(ns coffeepot.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [coffeepot.events :as events]
            [coffeepot.views :as views]
            [coffeepot.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (enable-console-print!)
  (println "Attemptin to initialize")
  (js/console.log "Let's get things started.")
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch [::events/initialize-firebase])
  (dev-setup)
  (mount-root))
