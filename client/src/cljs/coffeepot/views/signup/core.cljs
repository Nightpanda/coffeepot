(ns coffeepot.views.signup.core
    (:require [re-frame.core :as re-frame]
              [reagent.core :as r]
              [re-com.core :as re-com]
              [clojure.string :as str]
              [cljsjs.material-ui]
              [cljs-react-material-ui.reagent :as ui]
              [cljs-react-material-ui.icons :as ic]
              [taoensso.timbre :as timbre :refer-macros [debug info warn error]]
              [coffeepot.events :as events]
              [coffeepot.subs :as subs]
              [coffeepot.localization :refer [localize localize-with-substitute]]
              [coffeepot.components.styles :as styles]))

(defn abort-sign-up! []
  (re-frame/dispatch [::events/sub-view ""])
  (println "Aborting"))

(def register-form
  (fn []
    (let [user-uid (re-frame/subscribe [::subs/user-uid])
          new-username (r/atom "")]
      [ui/card
       [ui/card-text
        [:input.form-control
         {:on-change #(reset! new-username (-> % .-target .-value))}]]
       [ui/card-text (localize :link-auth-username)]
       [ui/card-actions
        ;; [firebase/listen-firebase-db (str "usernames/")
        ;;  (fn [usernames]
        ;;    (if (some? @usernames)
        ;;      (if (empty? (filter #(= % @new-username) (js/Object.keys @usernames)))
        ;;        (do
        ;;          [ui/card-text (localize :no-username-found)]
        ;;          [ui/flat-button {:on-click (fn []
        ;;                                       (println "Save to db")
        ;;                                       (re-frame/dispatch [::events/sub-view ""])) :label (localize :save-username)}])
        ;;        [ui/card-text (localize :username-warning)])))]
        ]])))

(def authenticate-step
  (fn []
    (let [user-uid (re-frame/subscribe [::subs/user-uid])]
      (if (nil? @user-uid)
        [ui/card
         [ui/card-header [ui/flat-button {:on-click (fn []
                                                      (println "sinneeee"))
                                          :label (localize-with-substitute :signup-with (localize :google))} ]]
         [:img {:src "images/btn_google_signin_light_normal_web.png"}]]
        [ui/card
         [ui/card-header (localize :authenticated)]]))))

(defn step-> [step]
  (let [inc-step (inc step)]
    (if (> inc-step 1)
      1
      inc-step)))

(defn step<- [step]
  (let [dec-step (dec step)]
    (if (neg? dec-step)
      0
      dec-step)))

(def user-description-step
  (fn []
    (let [user-uid (re-frame/subscribe [::subs/user-uid])
          username (re-frame/subscribe [::subs/username])
          user-description (re-frame/subscribe [::subs/user-description])
          new-user-description (r/atom "")]
      [ui/card
       [ui/card-header (localize :add-description)]
       [ui/card-text
        [:input.form-control
         {:on-change #(reset! new-user-description (-> % .-target .-value))}]]
       [ui/flat-button {:on-click (fn []
                                    (println "save user desc"))
                        :label (localize :save-description)}]
       (if (nil? @user-description)
         [ui/flat-button {:on-click
                          (fn [] (re-frame/dispatch [::events/user-description ""])
                           (println "save user desc"))
                          :label (localize :maybe-later)}])])))

(def sign-up-form-orig
  (let [user-uid (re-frame/subscribe [::subs/user-uid])
        username (re-frame/subscribe [::subs/username])
        initial-step (cond
                       (some? @user-uid) 1
                       :else 0)
        step (r/atom initial-step)]
    (fn []
      [ui/card
       [ui/card-text
        (cond
          (zero? @step) [authenticate-step]
          (= @step 1) [register-form])]
       [ui/stepper {:activeStep @step}
        [ui/step
         [ui/step-label (localize :authenticate)]]
        [ui/step
         [ui/step-label (localize :register-username)]]]
       [ui/card-actions
        (if (pos? @step)
          [ui/flat-button {:on-click #(swap! step step<-)} (localize :back)]
          [ui/flat-button {:on-click #(abort-sign-up!) :label (localize :cancel)}])
        (cond
          (zero? @step) (if (some? @user-uid)
                        [ui/raised-button {:on-click #(swap! step step->)} (localize :next)]))]])))

(defn sign-up-with-options []
  [re-com/h-box
   :width "50%"
   :justify :center
   :children [[re-com/v-box
               :gap "1em"
               :align :center
               :children [[ui/card-text (localize :select-sign-up-method)]
                          [re-com/button
                           :attr {:id "google-sign-up"}
                           :on-click (fn []
                                       (println "do stuff")
                                       ;; (let [firebase-app (re-frame/subscribe [::subs/firebase-app])]
                                       ;;   (firebase/signInWithProvider :google))
                                       )
                           :label [re-com/h-box
                                   :width "200px"
                                   :children [[:img {:src "images/g-logo.png"
                                                     :width "24px"
                                                     :height "24px"}]
                                              [re-com/box
                                               :align :center
                                               :justify :center
                                               :size "auto"
                                               :child (localize-with-substitute :signup-with (localize :google))]]]]]]]])

(def password-min-length 6)

(defn create-email-user [email password]
  )

(defn sign-up-with-email [email-signup-open? step]
  (let [email-address (r/atom "")
        password (r/atom "")
        password-again (r/atom "")]
    (fn [email-signup-open? step]
      (let [email-length-ok? (>= (count (str/trim @email-address)) 3)
            email-valid? (and (true? email-length-ok?) (str/includes? @email-address "@"))
            password-length-ok? (>= (count (str/trim @password)) password-min-length)
            passwords-match-ok? (and (= @password @password-again) (true? password-length-ok?))
            signup-data-ok? (and (true? email-valid?) (true? passwords-match-ok?))]
        [re-com/h-box
         :width "50%"
         :justify :center
         :size "0 0 auto"
         :children [(if (true? @email-signup-open?)
                      [re-com/v-box
                       :gap "1em"
                       :align :stretch
                       :children [[re-com/v-box
                                   :gap "1em"
                                   :size "0 0 auto"
                                   :children [[re-com/h-box
                                               :align :center
                                               :gap "0.2em"
                                               :children [[re-com/input-text
                                                           :attr {:id "email-address-input"}
                                                           :width "90%"
                                                           :model ""
                                                           :change-on-blur? false
                                                           :on-change (fn [new-value]
                                                                        (reset! email-address new-value))
                                                           :placeholder (localize :email-address)]
                                                          [ic/navigation-check
                                                           {:color "green"
                                                            :style {:visibility (if (true? email-valid?) "" "hidden")}}]]]
                                              [re-com/h-box
                                               :align :center
                                               :gap "0.2em"
                                               :children [[re-com/input-text
                                                           :attr {:id "email-password-initial-input"}
                                                           :width "90%"
                                                           :model ""
                                                           :input-type :password
                                                           :change-on-blur? false
                                                           :on-change (fn [new-value]
                                                                        (reset! password new-value))
                                                           :placeholder (localize :password)]]]
                                              [re-com/h-box
                                               :align :center
                                               :gap "0.2em"
                                               :children [[re-com/input-text
                                                           :attr {:id "email-password-confirmation-input"}
                                                           :width "90%"
                                                           :model ""
                                                           :input-type :password
                                                           :change-on-blur? false
                                                           :on-change (fn [new-value]
                                                                        (reset! password-again new-value))
                                                           :placeholder (localize :password-again)]
                                                          [ic/navigation-check
                                                           {:color "green"
                                                            :style {:visibility (if (true? passwords-match-ok?) "" "hidden")}}]]]]]
                                  [re-com/box
                                   :padding "2em 0em 0em 0em"
                                   :child [re-com/button
                                           :attr {:id "sign-up-continue"}
                                           :on-click (fn []
                                                       (if (true? signup-data-ok?)
                                                         (do
                                                           (create-email-user @email-address @password)
                                                           (reset! step 1))))
                                           :style {:color "white"
                                                   :background-color (if (true? signup-data-ok?) "green" "gray")}
                                           :disabled? (not signup-data-ok?)
                                           :label [re-com/h-box
                                                   :width "200px"
                                                   :children [[re-com/box
                                                               :align :center
                                                               :justify :center
                                                               :size "auto"
                                                               :child (localize :continue)]]]]]]]
                      [re-com/v-box
                       :children [[re-com/button
                                   :attr {:id "email-sign-up"}
                                   :on-click #(reset! email-signup-open? (not @email-signup-open?))
                                   :label [re-com/h-box
                                           :width "200px"
                                           :children [[ic/communication-email]
                                                      [re-com/box
                                                       :align :center
                                                       :justify :center
                                                       :size "auto"
                                                       :child (localize :register-with-email)]]]]]])]]))))

(defn select-sign-up-method [email-signup-open? step]
  (fn [email-signup-open? step]
    [re-com/v-box
     :align :center
     :padding "0em 0em 2em 0em"
     :children [[sign-up-with-options]
                [ui/card-text "------ " (localize :or) " ------"]
                [sign-up-with-email email-signup-open? step]]]))

(def user-name-min-length 3)

(defn fill-account-details [terms-accepted?]
  (let [user-uid (re-frame/subscribe [::subs/user-uid])
        new-username (r/atom "")]

    ;; (fn [usernames]
    ;;   (let [username-unique? (if (some? @usernames)
    ;;                            (empty? (filter #(= (str/lower-case %) (str/lower-case @new-username)) (js/Object.keys @usernames)))
    ;;                            false)
    ;;         username-length-ok? (>= (count (str/trim @new-username)) user-name-min-length)
    ;;         username-valid? (and (true? username-unique?) (true? username-length-ok?))
    ;;         show-tooltip? (r/atom false)
    ;;         create-button-disabled? (or (not @terms-accepted?) (false? username-unique?) (false? username-length-ok?))]
    ;;     [re-com/v-box
    ;;      :gap "1em"
    ;;      :padding "0em 0em 2em 0em"
    ;;      :align :center
    ;;      :children [[re-com/v-box
    ;;                  :size "1 1 auto"
    ;;                  :gap "0.2em"
    ;;                  :children [[re-com/label
    ;;                              :label (localize :link-auth-username)]
    ;;                             [re-com/h-box
    ;;                              :align :center
    ;;                              :gap "0.1em"
    ;;                              :children [[re-com/input-text
    ;;                                          :attr {:id "username-input"}
    ;;                                          :width "90%"
    ;;                                          :model ""
    ;;                                          :change-on-blur? false
    ;;                                          :on-change (fn [new-value]
    ;;                                                       (reset! new-username new-value))
    ;;                                          :placeholder (localize :username-min)]
    ;;                                         (if (true? username-length-ok?)
    ;;                                           (if (true? username-unique?)
    ;;                                             [ic/navigation-check
    ;;                                              {:color "green"}]
    ;;                                             [re-com/popover-anchor-wrapper
    ;;                                              :showing? show-tooltip?
    ;;                                              :position :above-center
    ;;                                              :anchor [ic/content-clear
    ;;                                                       {:color "red"
    ;;                                                        :on-mouse-over (re-com/handler-fn (reset! show-tooltip? true))
    ;;                                                        :on-mouse-out (re-com/handler-fn (reset! show-tooltip? false))}]
    ;;                                              :popover [re-com/popover-content-wrapper
    ;;                                                        :body (localize :username-taken)
    ;;                                                        :close-button? false]]))]]]]
    ;;                 [re-com/checkbox
    ;;                  :attr {:id "agree-user-terms"}
    ;;                  :model @terms-accepted?
    ;;                  :label (localize :terms-read-and-accepted) ;;add link to terms. Clicking terms/käyttöehdot (italic) open those
    ;;                  :on-change (fn [new-value]
    ;;                               (reset! terms-accepted? new-value))]
    ;;                 [re-com/box
    ;;                  :child [re-com/button
    ;;                          :attr {:id "create-account"}
    ;;                          :on-click (fn []
    ;;                                      (println "save usernameuid")
    ;;                                      (re-frame/dispatch [::events/sub-view ""]))
    ;;                          :style {:color "white"
    ;;                                  :background-color (if (true? create-button-disabled?) "gray" "green")}
    ;;                          :disabled? create-button-disabled?
    ;;                          :label [re-com/h-box
    ;;                                  :width "200px"
    ;;                                  :children [[re-com/box
    ;;                                              :align :center
    ;;                                              :justify :center
    ;;                                              :size "auto"
    ;;                                              :child (localize :create-account-button)]]]]]]]))
    ))

(defn sign-up-form [user-uid]
  (let [step (r/atom 0)
        email-signup-open? (r/atom false)
        terms-accepted? (r/atom false)]
    (fn [user-uid]
      [ui/card
       [ui/stepper {:activeStep @step}
        [ui/step
         [ui/step-label
          (localize :select-sign-up-method)]]
        [ui/step
         [ui/step-label
          (localize :fill-account-details)]]]
       [:div
        (cond
          (zero? @step) [select-sign-up-method email-signup-open? step]
          (= @step 1) [fill-account-details terms-accepted?])]])))

(defn signup-modal []
  (let [sub-view (re-frame/subscribe [::subs/sub-view])
        user-uid (re-frame/subscribe [::subs/user-uid])]
    [ui/dialog
     {:title (localize :create-account)
      :open (= @sub-view :sign-up)
      :modal false
      :onRequestClose #(re-frame/dispatch [::events/sub-view ""])}
     [sign-up-form user-uid]]))

(defn user-description-modal []
  (let [user-uid (re-frame/subscribe [::subs/user-uid])
        username (re-frame/subscribe [::subs/username])
        description (re-frame/subscribe [::subs/user-description])]
    [ui/dialog
     {:title (localize-with-substitute :hello @username)
      :open (and (some? @user-uid) (some? @username) (nil? @description))
      :modal false
      :onRequestClose (fn [] (re-frame/dispatch [::events/description ""]))}
     [user-description-step]]))
