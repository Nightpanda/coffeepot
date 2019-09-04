(ns webworkers.serviceworker
  (:require [taoensso.timbre :as timbre :refer-macros [debug info warn error]]))

(def cache-files ["/cljs-out/dev-main.js"])

(defn- install-service-worker [event]
  (info "Installing PWA service worker")
  (-> js/caches
      (.open "Coffeepot")
      (.then (fn [cache]
               (info "Caching files")
               (.addAll cache (clj->js cache-files))))
      (.then (fn []
               (info "PWA service worker successfully installed"))))
  )

(.addEventListener js/self "install" #(.waitUntil % (install-service-worker %)))
