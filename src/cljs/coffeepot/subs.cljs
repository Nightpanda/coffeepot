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
