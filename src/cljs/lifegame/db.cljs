(ns lifegame.db
  (:require [cljs.spec.alpha :as s]))

;; -- Spec ------------------------------

(s/def ::pos-y int?)
(s/def ::pos-x int?)
(s/def ::pos (s/tuple ::pos-y ::pos-x))
(s/def ::dead? boolean?)
(s/def ::world (s/and (s/map-of ::pos ::dead?)
                      #(instance? PersistentTreeMap %))) ;; is a sorted-map
(s/def ::db (s/keys :req-un [::world]))

(def size [10 10])

(def default-db
  {:world (->> (for [x (range (first size))
                     y (range (last size))]
                 [[x y] false])

               (into (sorted-map)))
   :size size})
