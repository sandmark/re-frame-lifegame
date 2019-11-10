(ns lifegame.views
  (:require [re-frame.core :as re-frame]
            [lifegame.subs :as subs]
            [lifegame.events :as events]))

(defn life-cell [[x y]]
  (let [world @(re-frame/subscribe [::subs/world])
        dead? (world [x y])]
    [:td {:class    (if dead? "dead" "living")
          :on-click #(re-frame/dispatch [::events/toggle-life [x y]])}]))

(defn world-panel []
  (let [[x y] @(re-frame/subscribe [::subs/size])]
    [:div.world
     [:table
      (for [y' (range y)]
        [:tr
         (for [x' (range x)]
           [life-cell [x' y']])])]]))
