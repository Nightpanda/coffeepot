(ns coffeepot.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 ::firebase-app
 (fn [db]
   (:firebase-app db)))

(re-frame/reg-sub
 ::auth-listener
 (fn [db]
   (:auth-listener db)))

(re-frame/reg-sub
 ::user
 (fn [db]
   (:user db)))

(re-frame/reg-sub
 ::locale
 (fn [db]
   (:locale db)))

(re-frame/reg-sub
  ::current-view
  (fn [db]
    (:current-view db)))

(re-frame/reg-sub
  ::sub-view
  (fn [db]
    (:sub-view db)))
