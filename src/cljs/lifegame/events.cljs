(ns lifegame.events
  (:require [re-frame.core :as re-frame]
            [lifegame.db :as db]))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(defn toggle-life [pos db]
  (update-in db [:world pos] not))

(re-frame/reg-event-db
 ::toggle-life
 (fn [db [_ pos]]
   (toggle-life pos db)))

(defn neighbours [[x y]]
  (let [xs (range (- x 1) (+ x 2))
        ys (range (- y 1) (+ y 2))]
    (->> (for [x' xs, y' ys] [x' y'])
         (remove (partial = [x y])))))

(defn collect-neighbours [pos world]
  (->> (neighbours pos)
       (map world)))

(defn thrive [world pos life]
  (let [lives-count (->> (collect-neighbours pos world)
                         (filter identity)
                         count)]
    (cond
      ;; Reproduction
      (and (not life) (= lives-count 3))
      true

      ;; Underpopulation
      (and life (<= lives-count 1))
      false

      ;; Overpopulation
      (and life (>= lives-count 4))
      false

      ;; Alive
      (and life (or (= lives-count 2)
                    (= lives-count 3)))
      true)))

(re-frame/reg-event-db
 ::thrive-all
 (fn [db]
   (let [world (:world db)]
     (letfn [(f [m k v]
               (assoc m k (thrive world k v)))]
       (assoc-in db [:world] (reduce-kv f {} world))))))
