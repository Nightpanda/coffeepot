(ns coffeepot.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::active-page
 :active-page)

(re-frame/reg-sub
 ::name
 :name)

(re-frame/reg-sub
 ::firebase-app
 :firebase-app)

(re-frame/reg-sub
 ::auth-listener
 :auth-listener)

(re-frame/reg-sub
 ::user
 :user)

(re-frame/reg-sub
 ::locale
 :locale)

(re-frame/reg-sub
 ::current-view
 :current-view)

(re-frame/reg-sub
 ::sub-view
 :sub-view)

(re-frame/reg-sub
 ::username
 :username)

(re-frame/reg-sub
 ::user-uid
 :user-uid)

(re-frame/reg-sub
 ::user-description
 :user-description)
