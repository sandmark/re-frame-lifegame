(ns lifegame.views
  (:require [re-frame.core :as re-frame]
            [lifegame.subs :as subs]
            [lifegame.events :as events]
            [reagent.core :as reagent]))

(defn life-cell [[x y]]
  (let [world @(re-frame/subscribe [::subs/world])
        dead? (world [x y])]
    [:td {:class    (if dead? "dead" "living")
          :on-click #(re-frame/dispatch [::events/toggle-life [x y]])}]))

(defn world-panel []
  (let [[x y] @(re-frame/subscribe [::subs/size])]
    [:div.world
     [:table
      [:tbody
       (for [y' (range y)]
         [:tr
          (for [x' (range x)]
            ^{:key (str x' y')} [life-cell [x' y']])])]]]))

(defn next-button []
  [:button {:on-click #(re-frame/dispatch [::events/thrive-all])}
   "Next"])

(defn play-button []
  (let [timer (reagent/atom nil)
        val   (reagent/atom "Play")]
    (letfn [(thrive-all []
              (re-frame/dispatch [::events/thrive-all]))
            (toggle-timer [interval]  ;; TODO: This needs to be more readable
              (if @timer
                (do (swap! timer #(js/clearInterval %))
                    (reset! val "Replay"))
                (do (swap! timer #(js/setInterval thrive-all interval))
                    (reset! val "Playing..."))))]
      (fn []
        [:button {:on-click #(toggle-timer 1000)}
         @val]))))

(defn random-button []
  [:button {:on-click #(re-frame/dispatch [::events/random-lives])}
   "Random"])

(defn app-panel []
  [:div
   [play-button]
   [next-button]
   [random-button]
   [world-panel]])
