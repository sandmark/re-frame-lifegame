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

(defn update-world [m f]
  (reduce-kv (fn [coll k v]
               (assoc coll k (f m k v))) {} m))

(re-frame/reg-event-db
 ::thrive-all
 (fn [db]
   (update-in db [:world] update-world thrive)))

(re-frame/reg-event-db
 ::random-lives
 (fn [db]
   (update-in db [:world] update-world #(rand-nth [true false]))))
