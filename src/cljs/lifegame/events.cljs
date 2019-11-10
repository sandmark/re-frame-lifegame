(ns lifegame.events
  (:require [re-frame.core :as re-frame]
            [lifegame.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-db
 ::toggle-life
 (fn [db [_ pos]]
   (update-in db [:world pos] not)))
