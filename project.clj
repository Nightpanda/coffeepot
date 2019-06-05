(defproject coffeepot "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.339"]
                 [reagent "0.7.0" :exclusions [cljsjs/react cljsjs/react-dom]]
                 [re-frame "0.10.2" :exclusions [cljsjs/react cljsjs/react-dom]]
                 [secretary "1.2.3"]
                 [cljs-react-material-ui "0.2.48"]
                 [cljsjs/react "15.6.1-1"]
                 [cljsjs/react-dom "15.6.1-1"]
                 [com.andrewmcveigh/cljs-time "0.5.0"]
                 [org.clojure/core.async "0.2.391"]
                 [re-com "2.1.0"]
                 [re-frisk "0.5.3"]
                 [com.taoensso/timbre "4.10.0"]
                 [tongue "0.2.3"]]

  :min-lein-version "2.5.3"

  :profiles
  {:dev
   {:dependencies [[com.bhauman/figwheel-main "0.2.0"]
                   [com.bhauman/rebel-readline-cljs "0.1.4"]
                   [binaryage/devtools "0.9.4"]]
    :resource-paths ["target"]
    :source-paths   ["src/cljs" "src/webworkers"]
    :clean-targets ^{:protect false} ["target"]
    :plugins      [[lein-kibit "0.1.6"]]}}

  :aliases {"dev" ["trampoline" "run" "-m" "figwheel.main" "-bb" "webworker" "-b" "dev" "-r"]}
)
