(ns coffeepot.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
            [coffeepot.app :as app]
            [coffeepot.config :as config]
            [coffeepot.events :as events]
            [coffeepot.firebase :as firebase]))

(defn timbre-setup []
  (timbre/set-level! :info))

(defn dev-setup []
  (when config/debug?
    (timbre/set-level! :debug))
  (timbre/debug "dev mode"))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [app/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (timbre-setup)
  (dev-setup)
  (debug "Attemptin to initialize")
  (debug "Let's get things started.")
  (re-frame/dispatch-sync [::events/initialize-db])
  (firebase/initialize-firebase)
  (mount-root))
