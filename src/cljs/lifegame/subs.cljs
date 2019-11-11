(ns lifegame.subs
  (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::world
 (fn [db]
   (:world db)))

(re-frame/reg-sub
 ::size
 (fn [db]
   (:size db)))
