(ns coffeepot.events
  (:require [re-frame.core :as re-frame]
            [reagent.core :as r]
            [coffeepot.db :as db]
            [coffeepot.subs :as subs]))

(re-frame/reg-event-db
 ::set-page
 (fn [db [_ active-page]]
   (assoc db :active-page active-page)))

(re-frame/reg-event-db
 ::initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::change-auth-listener-status
 (fn [db  [_ status]]
   (assoc db :auth-listener status)))

(re-frame/reg-event-db
 ::user-logged-in
 (fn [db  [_ logged-in]]
   (assoc-in db [:user :logged-in] logged-in)))

(re-frame/reg-event-db
 ::change-locale
 (fn [db  [_ locale]]
   (assoc db :locale locale)))

(re-frame/reg-event-db
  ::current-view
  (fn [db  [_ current-view]]
    (assoc db :current-view current-view)))

(re-frame/reg-event-db
  ::sub-view
  (fn [db  [_ sub-view]]
    (assoc db :sub-view sub-view)))

(re-frame/reg-event-db
 ::user-uid
 (fn [db [_ uid]]
   (assoc db :user-uid uid)))

(re-frame/reg-event-db
 ::username
 (fn [db [_ username]]
   (assoc db :username username)))

(re-frame/reg-event-db
  ::user-description
  (fn [db [_ description]]
    (assoc db :user-description description)))
