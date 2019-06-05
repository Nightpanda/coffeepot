(ns coffeepot.core
  (:require
            [reagent.core :as reagent]
            [re-frame.core :as re-frame]
            [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
            [coffeepot.app :as app]
            [coffeepot.events :as events]
            [coffeepot.config :as config]
            [coffeepot.firebase :as firebase]))

(defn timbre-setup []
  (timbre/set-level! :info))

(defn dev-setup []
  (when config/debug?
    (timbre/set-level! :debug))
  (timbre/debug "dev mode"))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [app/app-root]
                  (.getElementById js/document "app")))

(defn- pwafy! []
 (when js/navigator.serviceWorker (.register js/navigator.serviceWorker "cljs-out/webworker-main.js")))

(defn ^:export init []
  (timbre-setup)
  (dev-setup)
  (pwafy!)
  (debug "Initializing coffeepot...")
  (debug "Grinding some beans...")
  (app/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (firebase/initialize-firebase)
  (mount-root))
